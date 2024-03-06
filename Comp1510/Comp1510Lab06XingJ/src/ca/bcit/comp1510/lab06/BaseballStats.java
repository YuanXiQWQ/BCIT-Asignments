package ca.bcit.comp1510.lab06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reads baseball data in from a comma-delimited file. Each line
 * of the file contains a name followed by a list of symbols
 * indicating the result of each at bat: h for hit, o for out,
 * w for walk, s for sacrifice. Statistics are computed and
 * printed for each player.
 *
 * @author blink
 * @author Xing Jiarui
 * @version 2024.2.22
 */
public class BaseballStats {
    /**
     * Reads baseball stats from a file and counts
     * total hits, outs, walks, and sacrifice flies
     * for each player.
     *
     * @param args unused
     * @throws FileNotFoundException if file is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        //Initialise
        //Scanner
        /* I commented out these scanners so that the following steps might be clearer
        //To read in a file name from standard input.
        Scanner scan;
        //To operate on that file.
        Scanner fileScan;
        //To parse each line in the file.
        Scanner lineScan;
        */
        String fileName;
        //Use System.in as parameter to shi li hua 实例化 Scanner scan
        Scanner scan = new Scanner(System.in);

        //Interaction
        //TODO Read and process each line of the file
        System.out.print("Enter the path of the input file: ");
        //Get the file path from System.in with Scanner object scan.
        fileName = scan.nextLine();

        //Get this file from the path.(Creat and 实例化 File object)
        File content = new File(fileName);
        //Get the content from this file.(Scanner object)
        Scanner fileScan = new Scanner(content);
        //Read each line of the content.(Stop condition: !hasNextLine)
        while (fileScan.hasNextLine()) {
            //Read the next line from the file.
            String line = fileScan.nextLine();
            //Create a comma delimited scanner (lineScan) to parse the line.
            //.useDelimiter(",") is used to use comma as the delimiter.
            Scanner lineScan = new Scanner(line).useDelimiter(",");
            //Read the name of the player. The 0th one of each line is the name.
            String name = lineScan.next();

            //The loop that prints each at bat code.
            int hits = 0, outs = 0, walks = 0, sacrifices = 0;
            //Calculate each mark. Stop condition: hasNext("," as the delimiter)
            while (lineScan.hasNext()) {
                //0th one is the name, from the 1st one is the at bat code.
                String result = lineScan.next();
                switch (result) {
                    case "h":
                        hits++;
                        break;
                    case "o":
                        outs++;
                        break;
                    case "w":
                        walks++;
                        break;
                    case "s":
                        sacrifices++;
                        break;
                }
            }

            //Compute the batting average
            double battingAverage;
            //Check if divisor is 0; 0 cannot be used as a divisor.
            if (hits + outs == 0) {
                battingAverage = 0.0;
            } else {
                battingAverage = (double) hits / (double) (hits + outs);
            }

            //Output
            System.out.println(name
                    + " - Hits: " + hits
                    + ", Outs:" + outs
                    + ", Walks: " + walks
                    + ", Sacrifices: " + sacrifices
                    /* Keep three decimal places. */
                    + ", Batting Average: " + String.format("%.3f", battingAverage)
            );

            lineScan.close();
        }

        fileScan.close();
        scan.close();
    }
}