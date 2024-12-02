import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Manages a collection of countries loaded from data files. Used in the Word Game to
 * provide country data for questions.
 *
 * @author Jiarui Xing
 */
public class World {
    private final HashMap<String, Country> countries;

    /**
     * Constructs an empty World.
     */
    public World()
    {
        countries = new HashMap<>();
    }

    /**
     * Loads countries from the specified file and adds them to the world.
     *
     * @param filename the name of the file to load countries from
     */
    public void loadCountries(final String filename)
    {
        try(BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String line;
            String name = null;
            String capital = null;
            String[] facts = new String[3];
            int factIndex = 0;

            while((line = br.readLine()) != null)
            {
                line = line.trim();
                if(line.isEmpty())
                {
                    // Empty line indicates separation between countries
                    if(name != null && factIndex > 0)
                    {
                        final Country country = new Country(name, capital, facts);
                        countries.put(name, country);
                        // Reset variables for the next country
                        name = null;
                        capital = null;
                        facts = new String[3];
                        factIndex = 0;
                    }
                } else if(line.contains(":"))
                {
                    // Line contains country and capital
                    final String[] parts = line.split(":", 2);
                    name = parts[0].trim();
                    capital = parts[1].trim();
                } else
                {
                    // Line contains a fact
                    if(factIndex < facts.length)
                    {
                        facts[factIndex++] = line;
                    }
                }
            }
            // Add the last country if the file doesn't end with an empty line
            if(name != null && factIndex > 0)
            {
                final Country country = new Country(name, capital, facts);
                countries.put(name, country);
            }

        } catch(IOException e)
        {
            System.out.println("Error reading file: " + filename);
            e.printStackTrace();
        }
    }

    /**
     * Gets the HashMap of countries.
     *
     * @return the countries HashMap
     */
    public HashMap<String, Country> getCountries()
    {
        return countries;
    }
}
