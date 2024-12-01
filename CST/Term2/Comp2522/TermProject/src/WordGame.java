import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Implements the Word Game where users answer geography trivia questions. Loads country
 * data, manages game flow, scoring, and high score tracking.
 *
 * @author Jiarui Xing
 */
public class WordGame implements Game {
    private final World world;
    private final Scanner scanner;
    private final List<Score> scores;
    private final String SCORE_FILE = "score.txt";
    private final Random random = new Random();
    private static final int QUESTIONS_PER_GAME = 10;
    private static final int MAX_FACTS = 3;

    /**
     * Constructs a WordGame and initializes necessary components.
     */
    public WordGame()
    {
        world = new World();
        loadAllCountryFiles();
        scanner = new Scanner(System.in);
        List<Score> tempScores;
        try
        {
            tempScores = Score.readScoresFromFile(SCORE_FILE);
        } catch(IOException e)
        {
            System.out.println("Error reading score file.");
            tempScores = new ArrayList<>();
        }
        scores = tempScores;
    }

    /**
     * Loads all country files from a-z and adds them to the world.
     */
    private void loadAllCountryFiles()
    {
        for(char c = 'a'; c <= 'z'; c++)
        {
            String filename = c + ".txt";
            File file = new File(filename);
            if(file.exists())
            {
                world.loadCountries(filename);
            }
        }
    }

    /**
     * Starts the Word Game, handling game loops and user interactions.
     */
    @Override
    public void start()
    {
        boolean playAgain = true;
        int totalGamesPlayed = 0;
        int totalCorrectFirst = 0;
        int totalCorrectSecond = 0;
        int totalIncorrect = 0;

        while(playAgain)
        {
            GameResult result = playSingleGame();
            totalGamesPlayed += 1;
            totalCorrectFirst += result.correctFirst;
            totalCorrectSecond += result.correctSecond;
            totalIncorrect += result.incorrect;

            System.out.printf(
                    "- %d word game(s) played\n- %d correct answers on the first " +
                            "attempt\n- %d correct answers on the second attempt\n- %d " +
                            "incorrect answers on two attempts each%n", totalGamesPlayed,
                    totalCorrectFirst, totalCorrectSecond, totalIncorrect);

            // Ask if the user wants to play again
            boolean validResponse = false;
            while(!validResponse)
            {
                System.out.print("Do you want to play again? (Yes/No): ");
                String response = scanner.nextLine().trim().toLowerCase();
                if("yes".equals(response))
                {
                    validResponse = true;
                } else if("no".equals(response))
                {
                    validResponse = true;
                    playAgain = false;
                } else
                {
                    System.out.println("Invalid input. Please enter Yes or No.");
                }
            }
        }

        final Score finalScore =
                new Score(LocalDateTime.now(), totalGamesPlayed, totalCorrectFirst,
                        totalCorrectSecond, totalIncorrect);

        // Check for high score
        checkHighScore(finalScore);

        // After exiting, save the score
        try
        {
            Score.appendScoreToFile(finalScore, SCORE_FILE);
            scores.add(finalScore);
        } catch(IOException e)
        {
            System.out.println("Error writing to score file.");
        }
    }

    /**
     * Plays a single game session, asking QUESTIONS_PER_GAME questions.
     *
     * @return the result of the game session
     */
    private GameResult playSingleGame()
    {
        int correctFirst = 0;
        int correctSecond = 0;
        int incorrect = 0;

        // Prepare list of countries
        List<Country> countryList = new ArrayList<>(world.getCountries().values());
        if(countryList.size() < QUESTIONS_PER_GAME)
        {
            System.out.println("Not enough countries loaded to play the game.");
            return new GameResult(correctFirst, correctSecond, incorrect);
        }

        // Shuffle and select questions
        Collections.shuffle(countryList);
        List<Country> selectedCountries = countryList.subList(0, QUESTIONS_PER_GAME);

        for(Country country : selectedCountries)
        {
            int questionType = random.nextInt(3);
            String question;
            String answer;
            String userAnswer;

            switch(questionType)
            {
                case 0:
                    // Show capital, ask for country
                    question = String.format("What country has the capital city %s?",
                            country.getCapitalCityName());
                    answer = country.getName();
                    break;
                case 1:
                    // Show country, ask for capital
                    question = String.format("What is the capital city of %s?",
                            country.getName());
                    answer = country.getCapitalCityName();
                    break;
                case 2:
                    // Show a fact, ask for country
                    String[] facts = country.getFacts();
                    int factIndex = random.nextInt(MAX_FACTS);
                    question = String.format(
                            "Which country is described by the following fact: %s",
                            facts[factIndex]);
                    answer = country.getName();
                    break;
                default:
                    // Should not reach here
                    continue;
            }

            System.out.println(question);
            // First attempt
            System.out.print("Your answer: ");
            userAnswer = scanner.nextLine().trim();
            if(userAnswer.equalsIgnoreCase(answer))
            {
                System.out.println("CORRECT");
                correctFirst += 1;
            } else
            {
                System.out.println("INCORRECT");
                // Second attempt
                System.out.print("Try again: ");
                userAnswer = scanner.nextLine().trim();
                if(userAnswer.equalsIgnoreCase(answer))
                {
                    System.out.println("CORRECT");
                    correctSecond += 1;
                } else
                {
                    System.out.println("INCORRECT");
                    System.out.printf("The correct answer was %s.%n", answer);
                    incorrect += 1;
                }
            }
        }

        return new GameResult(correctFirst, correctSecond, incorrect);
    }

    /**
     * Checks if the current score is a new high score and displays appropriate messages.
     *
     * @param currentScore the current score
     */
    private void checkHighScore(Score currentScore)
    {
        double averageScore = currentScore.getAverageScore();

        // Find the highest average score in existing scores
        double highestAverage = 0.0;
        LocalDateTime highScoreTime = null;
        if(!scores.isEmpty())
        {
            for(Score score : scores)
            {
                if(score == currentScore)
                {
                    continue;
                }
                double avg = score.getAverageScore();
                if(avg > highestAverage)
                {
                    highestAverage = avg;
                    highScoreTime = score.dateTimePlayed;
                }
            }
        }

        if(averageScore > highestAverage)
        {
            System.out.printf(
                    "CONGRATULATIONS! You are the new high score with an average of %" +
                            ".2f points per game; the previous record was %.2f points " +
                            "per game on %s.%n", averageScore, highestAverage,
                    highScoreTime != null
                    ? highScoreTime.format(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    : "N/A");
        } else
        {
            System.out.printf(
                    "You did not beat the high score of %.2f points per game from %s.%n",
                    highestAverage, highScoreTime != null
                                    ? highScoreTime.format(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                    : "N/A");
        }
    }

    /**
     * Inner class to hold the result of a game session.
     */
    private record GameResult(int correctFirst, int correctSecond, int incorrect) {
        /**
         * Constructs a GameResult with the given data.
         *
         * @param correctFirst  number of first-attempt correct answers
         * @param correctSecond number of second-attempt correct answers
         * @param incorrect     number of incorrect answers
         */
        private GameResult
        {
        }
    }
}
