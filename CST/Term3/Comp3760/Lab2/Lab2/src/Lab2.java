import java.util.ArrayList;
import java.util.Collections;

/**
 * COMP3760 Lab2 Generate all palindrome string sequences composed of characters A, B, and
 * C using a recursive algorithm with Decrease-by-a-Constant pattern.
 *
 * @author Jerry Xing | A01354731
 */
public class Lab2 {

    /**
     * Generate all palindrome sequences of the given length using the character set {A,
     * B, C}.
     *
     * @param length Target palindrome length
     * @return All palindrome sequences
     */
    public ArrayList<String> generatePalindromeSequences(int length)
    {
        // Base case
        if(length == 1)
        {
            ArrayList<String> basePalindromes = new ArrayList<>();
            basePalindromes.add("A");
            basePalindromes.add("B");
            basePalindromes.add("C");
            return basePalindromes;
        }

        if(length == 2)
        {
            ArrayList<String> basePalindromes = new ArrayList<>();
            basePalindromes.add("AA");
            basePalindromes.add("BB");
            basePalindromes.add("CC");
            return basePalindromes;
        }

        // General case
        ArrayList<String> subPalindromes = generatePalindromeSequences(length - 2);
        ArrayList<String> palindromes = new ArrayList<>();
        String[] alphabet = {"A", "B", "C"};

        for(String middle : subPalindromes)
        {
            for(String edgeSymbol : alphabet)
            {
                palindromes.add(edgeSymbol + middle + edgeSymbol);
            }
        }

        return palindromes;
    }


    /**
     * Generate all palindrome sequences of the given length with a customizable number of
     * colours.
     *
     * @param length     Target palindrome length
     * @param numColours Allowed number of colours
     * @return All palindrome sequences
     */
    public ArrayList<String> generatePalindromeSequencesCustom(int length, int numColours)
    {
        if(numColours < 1)
        {
            return new ArrayList<>();
        }

        String[] alphabet = new String[numColours];
        for(int colourIndex = 0; colourIndex < numColours; colourIndex++)
        {
            char letter = (char) ('A' + colourIndex);
            alphabet[colourIndex] = String.valueOf(letter);
        }

        // Base case
        if(length == 1)
        {
            ArrayList<String> basePalindromes = new ArrayList<>();
            Collections.addAll(basePalindromes, alphabet);
            return basePalindromes;
        }

        if(length == 2)
        {
            ArrayList<String> basePalindromes = new ArrayList<>();
            for(String edgeSymbol : alphabet)
            {
                basePalindromes.add(edgeSymbol + edgeSymbol);
            }
            return basePalindromes;
        }

        // General case
        ArrayList<String> subPalindromes =
                generatePalindromeSequencesCustom(length - 2, numColours);

        ArrayList<String> palindromes = new ArrayList<>();
        for(String middle : subPalindromes)
        {
            for(String edgeSymbol : alphabet)
            {
                palindromes.add(edgeSymbol + middle + edgeSymbol);
            }
        }

        return palindromes;
    }
}
