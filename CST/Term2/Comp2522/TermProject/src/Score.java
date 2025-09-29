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
public record Score(LocalDateTime dateTimePlayed, int numGamesPlayed,
                    int numCorrectFirstAttempt, int numCorrectSecondAttempt,
                    int numIncorrectTwoAttempts) {
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
    public Score
    {
    }

    /**
     * Calculates the total score based on correct answers.
     *
     * @return the total score
     */
    public int getScore()
    {
        final int firstAttemptPoints = 2;
        final int secondAttemptPoints = 1;
        return (numCorrectFirstAttempt * firstAttemptPoints) +
                (numCorrectSecondAttempt * secondAttemptPoints);
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
        final StringBuilder sb;
        sb = new StringBuilder()
                .append("Date and Time: ")
                .append(dateTimePlayed.format(FORMATTER))
                .append("\nGames Played: ")
                .append(numGamesPlayed)
                .append("\nCorrect First Attempts: ")
                .append(numCorrectFirstAttempt)
                .append("\nCorrect Second Attempts: ")
                .append(numCorrectSecondAttempt)
                .append("\nIncorrect Attempts: ")
                .append(numIncorrectTwoAttempts);
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
        return buildString()
                .append("\nScore: ")
                .append(getScore())
                .append(" points\n")
                .toString();
    }

    public String toFileString()
    {
        return buildString()
                .append("\nTotal Score: ")
                .append(getScore())
                .append(" points\n")
                .append("Average Score: ")
                .append(String.format("%.2f", getAverageScore()))
                .append(" points/game\n")
                .toString();
    }


    /**
     * Appends the score to the specified file.
     *
     * @param score    the score to append
     * @param filename the name of the file
     * @throws IOException if an I/O error occurs
     */
    public static void appendScoreToFile(final Score score, final String filename)
            throws IOException
    {
        try(final FileWriter fw = new FileWriter(filename, true);
            final BufferedWriter bw = new BufferedWriter(fw);
            final PrintWriter out = new PrintWriter(bw))
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
    public static List<Score> readScoresFromFile(final String filename) throws IOException
    {
        final List<Score> scores = new ArrayList<>();
        final File file = new File(filename);
        if(!file.exists())
        {
            return scores;
        }

        try(final BufferedReader br = new BufferedReader(new FileReader(file)))
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
                    final String dateTimeStr =
                            line.substring("Date and Time:".length()).trim();
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
                } else if(line.startsWith("Average Score:"))
                {
                    // End of one score entry, create Score object
                    if(dateTime != null)
                    {
                        final Score score = new Score(dateTime, numGamesPlayed,
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
