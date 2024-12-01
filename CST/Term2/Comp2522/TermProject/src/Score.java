import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a score record for the Word Game. Manages saving and loading scores from a
 * file. Used to track and compare high scores.
 *
 * @author Jiarui Xing
 */
public class Score{
    public final LocalDateTime dateTimePlayed;
    public final int numGamesPlayed;
    public final int numCorrectFirstAttempt;
    public final int numCorrectSecondAttempt;
    public final int numIncorrectTwoAttempts;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructs a Score with the given data.
     *
     * @param dateTimePlayed          the date and time when the score was recorded
     * @param numGamesPlayed          the number of games played
     * @param numCorrectFirstAttempt  the number of first-attempt correct answers
     * @param numCorrectSecondAttempt the number of second-attempt correct answers
     * @param numIncorrectTwoAttempts the number of incorrect answers after two attempts
     */
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

    /**
     * Calculates the total score based on correct answers.
     *
     * @return the total score
     */
    public int getScore()
    {
        final int FIRST_ATTEMPT_POINTS = 2;
        final int SECOND_ATTEMPT_POINTS = 1;
        return (numCorrectFirstAttempt * FIRST_ATTEMPT_POINTS) +
                (numCorrectSecondAttempt * SECOND_ATTEMPT_POINTS);
    }

    /**
     * Calculates the average score per game.
     *
     * @return the average score per game
     */
    public double getAverageScore()
    {
        return (double) getScore() / numGamesPlayed;
    }

    private StringBuilder buildString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("Date and Time: %s\n", dateTimePlayed.format(FORMATTER)));
        sb.append(String.format("Games Played: %d\n", numGamesPlayed));
        sb.append(String.format("Correct First Attempts: %d\n", numCorrectFirstAttempt));
        sb.append(
                String.format("Correct Second Attempts: %d\n", numCorrectSecondAttempt));
        sb.append(String.format("Incorrect Attempts: %d\n", numIncorrectTwoAttempts));
        return sb;
    }

    /**
     * Returns a string representation of the score in the required format.
     *
     * @return the formatted score string
     */
    @Override
    public String toString()
    {
        StringBuilder sb = buildString();
        sb.append(String.format("Score: %d points\n", getScore()));
        return sb.toString();
    }

    public String toFileString()
    {
        StringBuilder sb = buildString();
        sb.append(String.format("Total Score: %d points\n", getScore()));
        sb.append(String.format("Average Score: %.2f points/game\n", getAverageScore()));
        return sb.toString();
    }

    /**
     * Appends the score to the specified file.
     *
     * @param score    the score to append
     * @param filename the name of the file
     * @throws IOException if an I/O error occurs
     */
    public static void appendScoreToFile(Score score, String filename) throws IOException
    {
        try(FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(score.toFileString());
        }
    }

    /**
     * Reads all scores from the specified file.
     *
     * @param filename the name of the file
     * @return a list of scores
     *
     * @throws IOException if an I/O error occurs
     */
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
                    dateTime = LocalDateTime.parse(dateTimeStr, FORMATTER);
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
                    // Ignore, total score is calculated
                } else if(line.startsWith("Average Score:"))
                {
                    // End of one score entry, create Score object
                    if(dateTime != null)
                    {
                        Score score = new Score(dateTime, numGamesPlayed,
                                numCorrectFirstAttempt, numCorrectSecondAttempt,
                                numIncorrectTwoAttempts);
                        scores.add(score);
                        // Reset variables for next entry
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
