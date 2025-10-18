/**
 * Hash simulator that offers three string hash functions and linear probing insertion
 * logic, enabling collision and probe statistics across different hashing strategies for
 * a given input set.
 *
 * <p>Core capabilities include:</p>
 * <ul>
 *   <li>Fundamental methods to map name strings to numeric values</li>
 *   <li>Hash functions {@link #H1(String, int)}, {@link #H2(String, int)},
 *   {@link #H3(String, int)}</li>
 *   <li>Tools to insert keys into a linear-probing hash table and collect performance
 *   metrics</li>
 *   <li>{@link #runHashSimulation(String[], int)} for unified execution and result
 *   reporting</li>
 * </ul>
 *
 * @author Jerry Xing | A01354731
 */
public class HashSimulator {

    /**
     * Converts a letter to a numeric value from 1 to 26, ignoring non-letter characters
     * and remaining case-insensitive.
     *
     * @param character the character to convert
     * @return the corresponding letter value, or 0 if the character is not a letter
     */
    private int letterValue(char character)
    {
        char up = Character.toUpperCase(character);
        if(up >= 'A' && up <= 'Z')
        {
            return up - 'A' + 1;
        }
        return 0;
    }

    /**
     * Inserts a key into a closed-address hash table using linear probing and tracks
     * collisions and probe counts.
     *
     * @param table      the hash table array
     * @param key        the string key to insert
     * @param startIndex the initial bucket index
     * @return a 2-element array: index 0 indicates whether a collision occurred (0 or 1),
     * index 1 records additional probe attempts
     */
    private int[] insertWithLinearProbing(String[] table, String key, int startIndex)
    {
        int tableSize = table.length;
        if(table[startIndex] == null)
        {
            table[startIndex] = key;
            return new int[]{0, 0};
        }

        int collisions = 1;
        int probes = 0;
        int index = startIndex;

        while(true)
        {
            index = (index + 1) % tableSize;
            probes++;
            if(table[index] == null)
            {
                table[index] = key;
                return new int[]{collisions, probes};
            }
        }
    }

    /**
     * Executes insert simulations for three hash functions on a specified dataset,
     * gathering collision and probe counts.
     *
     * @param keys      the array of names to hash
     * @param tableSize the hash table size
     * @return a 6-element array containing, in order, collisions and probes for H1, H2,
     * and H3
     */
    public int[] runHashSimulation(String[] keys, int tableSize)
    {
        int[] results = new int[6];

        String[] tableH1 = new String[tableSize];
        int collisionsH1 = 0;
        int probesH1 = 0;
        for(String key : keys)
        {
            int idx = H1(key, tableSize);
            int[] r = insertWithLinearProbing(tableH1, key, idx);
            collisionsH1 += r[0];
            probesH1 += r[1];
        }
        results[0] = collisionsH1;
        results[1] = probesH1;

        String[] tableH2 = new String[tableSize];
        int collisionsH2 = 0;
        int probesH2 = 0;
        for(String key : keys)
        {
            int idx = H2(key, tableSize);
            int[] r = insertWithLinearProbing(tableH2, key, idx);
            collisionsH2 += r[0];
            probesH2 += r[1];
        }
        results[2] = collisionsH2;
        results[3] = probesH2;

        String[] tableH3 = new String[tableSize];
        int collisionsH3 = 0;
        int probesH3 = 0;
        for(String key : keys)
        {
            int idx = H3(key, tableSize);
            int[] r = insertWithLinearProbing(tableH3, key, idx);
            collisionsH3 += r[0];
            probesH3 += r[1];
        }
        results[4] = collisionsH3;
        results[5] = probesH3;

        return results;
    }

    /**
     * Hash function H1, summing letter values and taking the modulus with the table
     * length.
     *
     * @param name   the name string whose hash is needed
     * @param HTsize the hash table size
     * @return the hash table index, or 0 if {@code name} is {@code null}
     */
    public int H1(String name, int HTsize)
    {
        if(HTsize <= 0)
        {
            throw new IllegalArgumentException("HTsize must be > 0");
        }
        if(name == null)
        {
            return 0;
        }
        long sum = 0L;
        for(int pos = 0; pos < name.length(); pos++)
        {
            sum += letterValue(name.charAt(pos));
        }
        int hashValue = (int) (sum % HTsize);
        if(hashValue < 0)
        {
            hashValue += HTsize;
        }
        return hashValue;
    }

    /**
     * Hash function H2, accumulating letter values as a base-26 polynomial and taking the
     * modulus with the table length.
     *
     * @param name   the name string whose hash is needed
     * @param HTsize the hash table size
     * @return the hash table index, or 0 if {@code name} is {@code null}
     */
    public int H2(String name, int HTsize)
    {
        if(HTsize <= 0)
        {
            throw new IllegalArgumentException("HTsize must be > 0");
        }
        if(name == null)
        {
            return 0;
        }
        long hash = 0L;
        long pow = 1L % HTsize;
        for(int i = 0; i < name.length(); i++)
        {
            int val = letterValue(name.charAt(i));
            long add = (pow * val) % HTsize;
            hash = (hash + add) % HTsize;
            pow = (pow * 26L) % HTsize;
        }
        int hashValue = (int) hash;
        if(hashValue < 0)
        {
            hashValue += HTsize;
        }
        return hashValue;
    }

    /**
     * H3: DJB2-inspired string hash (non-cryptographic). Rationale: Update per character:
     * hash = hash * 33 + x; equivalently ((hash << 5) + hash) + x, i.e., shift left by 5
     * (multiply by 32) then add the original value to achieve *33. Multiplication by 33
     * can be efficiently implemented via "shift + add" and empirically yields good bit
     * diffusion and distribution for hash tables. Source: "Hash Functions" (York
     * University): <a href="https://www.cse.yorku.ca/~oz/hash.html">...</a>
     *
     * @param name   the name string whose hash is needed
     * @param HTsize the hash table size used to mod the hash value
     * @return the hash table index, or 0 if {@code name} is {@code null}
     */
    public int H3(String name, int HTsize)
    {
        if(HTsize <= 0)
        {
            throw new IllegalArgumentException("HTsize must be > 0");
        }
        if(name == null)
        {
            return 0;
        }
        long hash = 5381L;
        for(int i = 0; i < name.length(); i++)
        {
            int val = letterValue(name.charAt(i));
            if(val == 0)
            {
                continue;
            }
            hash = ((hash << 5) + hash) + val;
            hash %= HTsize;
        }
        int hashValue = (int) (hash % HTsize);
        if(hashValue < 0)
        {
            hashValue += HTsize;
        }
        return hashValue;
    }
}
