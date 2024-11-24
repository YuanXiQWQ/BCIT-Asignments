import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Score {
    private final LocalDateTime dateTimePlayed;
    private final int numGamesPlayed;
    private final int numCorrectFirstAttempt;
    private final int numCorrectSecondAttempt;
    private final int numIncorrectTwoAttempts;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "yyyy" +
                    "-MM" +
                    "-dd" +
                    " " +
                    "HH:mm:ss");

    public Score(LocalDateTime dateTimePlayed, int numGamesPlayed,
                 int numCorrectFirstAttempt, int numCorrectSecondAttempt,
                 int numIncorrectTwoAttempts)
    {
        this.dateTimePlayed = dateTimePlayed;
        this.numGamesPlayed = numGamesPlayed;
        this.numCorrectFirstAttempt = numCorrectFirstAttempt;
        this.numCorrectSecondAttempt = numCorrectSecondAttempt;
        this.numIncorrectTwoAttempts = numIncorrectTwoAttempts;
    }

    public int getScore()
    {
        return (numCorrectFirstAttempt * 2) + (numCorrectSecondAttempt);
    }

    @Override
    public String toString()
    {
        return String.format(
                "Date and Time: %s\nGames Played: %d\nCorrect First Attempts: " +
                        "%d\nCorrect Second Attempts: %d\nIncorrect Attempts: %d\nTotal" +
                        " Score: %d points\nAverage Score: %.2f points/game\n",
                dateTimePlayed.format(formatter),
                numGamesPlayed,
                numCorrectFirstAttempt,
                numCorrectSecondAttempt,
                numIncorrectTwoAttempts,
                getScore(),
                (double) getScore() / numGamesPlayed
        );
    }

    public static void appendScoreToFile(Score score, String filename) throws IOException
    {
        try(FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(score.toString());
        }
    }

    public static List<Score> readScoresFromFile(String filename) throws IOException
    {
        List<Score> scores = new ArrayList<>();
        File file = new File(filename);
        if(!file.exists())
        {
            return scores;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            LocalDateTime dateTime = null;
            int numGamesPlayed = 0;
            int numCorrectFirstAttempt = 0;
            int numCorrectSecondAttempt = 0;
            int numIncorrectTwoAttempts = 0;

            while((line = br.readLine()) != null)
            {
                if(line.startsWith("Date and Time:"))
                {
                    String dateTimeStr = line.substring("Date and Time:".length()).trim();
                    dateTime = LocalDateTime.parse(dateTimeStr, formatter);
                } else if(line.startsWith("Games Played:"))
                {
                    numGamesPlayed = Integer.parseInt(
                            line.substring("Games Played:".length()).trim());
                } else if(line.startsWith("Correct First Attempts:"))
                {
                    numCorrectFirstAttempt = Integer.parseInt(
                            line.substring("Correct First Attempts:".length()).trim());
                } else if(line.startsWith("Correct Second Attempts:"))
                {
                    numCorrectSecondAttempt = Integer.parseInt(
                            line.substring("Correct Second Attempts:".length()).trim());
                } else if(line.startsWith("Incorrect Attempts:"))
                {
                    numIncorrectTwoAttempts = Integer.parseInt(
                            line.substring("Incorrect Attempts:".length()).trim());
                } else if(line.startsWith("Total Score:"))
                {
                    // Ignore total score line as it's calculated
                    // After total score line, create a Score object
                    if(dateTime != null)
                    {
                        Score score = new Score(dateTime, numGamesPlayed,
                                numCorrectFirstAttempt, numCorrectSecondAttempt,
                                numIncorrectTwoAttempts);
                        scores.add(score);
                        // Reset for next entry
                        dateTime = null;
                        numGamesPlayed = 0;
                        numCorrectFirstAttempt = 0;
                        numCorrectSecondAttempt = 0;
                        numIncorrectTwoAttempts = 0;
                    }
                }
            }
        }

        return scores;
    }
}
