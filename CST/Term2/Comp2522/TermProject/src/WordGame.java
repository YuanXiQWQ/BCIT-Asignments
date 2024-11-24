// WordGame.java
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class WordGame {
    private final World world;
    private final Scanner scanner;
    private List<Score> scores;
    private final String SCORE_FILE = "score.txt";

    public WordGame() {
        world = new World();
        loadAllCountryFiles();
        scanner = new Scanner(System.in);
        try {
            scores = Score.readScoresFromFile(SCORE_FILE);
        } catch (IOException e) {
            System.out.println("Error reading score file.");
            scores = new ArrayList<>();
        }
    }

    private void loadAllCountryFiles() {
        // 假设所有国家文件都放在一个名为 "countries" 的文件夹中
        // 文件名为 a.txt, b.txt, ..., z.txt
        for (char c = 'a'; c <= 'z'; c++) {
            String filename = "countries/" + c + ".txt";
            File file = new File(filename);
            if (file.exists()) {
                world.loadCountries(filename);
            }
        }
    }

    public void start() {
        boolean playAgain = true;
        int totalGamesPlayed = 0;
        int totalCorrectFirst = 0;
        int totalCorrectSecond = 0;
        int totalIncorrect = 0;

        while (playAgain) {
            GameResult result = playSingleGame();
            totalGamesPlayed += 1;
            totalCorrectFirst += result.correctFirst;
            totalCorrectSecond += result.correctSecond;
            totalIncorrect += result.incorrect;

            System.out.println(String.format(
                    "- %d word games played\n- %d correct answers on the first attempt\n- %d correct answers on the second attempt\n- %d incorrect answers on two attempts each",
                    totalGamesPlayed, totalCorrectFirst, totalCorrectSecond, totalIncorrect
            ));

            // Ask if the user wants to play again
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("Do you want to play again? (Yes/No): ");
                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes")) {
                    validResponse = true;
                } else if (response.equals("no")) {
                    validResponse = true;
                    playAgain = false;
                } else {
                    System.out.println("Invalid input. Please enter Yes or No.");
                }
            }
        }

        // After exiting, save the score
        try {
            Score finalScore = new Score(
                    LocalDateTime.now(),
                    totalGamesPlayed,
                    totalCorrectFirst,
                    totalCorrectSecond,
                    totalIncorrect
            );
            Score.appendScoreToFile(finalScore, SCORE_FILE);
            scores.add(finalScore);
        } catch (IOException e) {
            System.out.println("Error writing to score file.");
        }

        // Check for high score
        checkHighScore(totalGamesPlayed, totalCorrectFirst, totalCorrectSecond, totalIncorrect);
    }

    private GameResult playSingleGame() {
        int correctFirst = 0;
        int correctSecond = 0;
        int incorrect = 0;

        // Prepare list of countries
        List<Country> countryList = new ArrayList<>(world.getCountries().values());
        if (countryList.size() < 10) {
            System.out.println("Not enough countries loaded to play the game.");
            return new GameResult(correctFirst, correctSecond, incorrect);
        }

        // Shuffle and select 10 questions
        Collections.shuffle(countryList);
        List<Country> selectedCountries = countryList.subList(0, 10);
        Random random = new Random();

        for (Country country : selectedCountries) {
            int questionType = random.nextInt(3); // 0, 1, or 2
            String question = "";
            String answer = "";
            String userAnswer = "";
            boolean correct = false;

            switch (questionType) {
                case 0:
                    // a) Show capital, ask for country
                    question = String.format("What country has the capital city %s?", country.getCapitalCityName());
                    answer = country.getName();
                    break;
                case 1:
                    // b) Show country, ask for capital
                    question = String.format("What is the capital city of %s?", country.getName());
                    answer = country.getCapitalCityName();
                    break;
                case 2:
                    // c) Show a fact, ask for country
                    String[] facts = country.getFacts();
                    int factIndex = random.nextInt(facts.length);
                    question = String.format("Which country is described by the following fact: %s", facts[factIndex]);
                    answer = country.getName();
                    break;
            }

            System.out.println(question);
            // First attempt
            System.out.print("Your answer: ");
            userAnswer = scanner.nextLine().trim();
            if (userAnswer.equalsIgnoreCase(answer)) {
                System.out.println("CORRECT");
                correctFirst += 1;
                continue;
            } else {
                System.out.println("INCORRECT");
                // Second attempt
                System.out.print("Try again: ");
                userAnswer = scanner.nextLine().trim();
                if (userAnswer.equalsIgnoreCase(answer)) {
                    System.out.println("CORRECT");
                    correctSecond += 1;
                } else {
                    System.out.println("INCORRECT");
                    System.out.println(String.format("The correct answer was %s.", answer));
                    incorrect += 1;
                }
            }
        }

        return new GameResult(correctFirst, correctSecond, incorrect);
    }

    private void checkHighScore(int gamesPlayed, int correctFirst, int correctSecond, int incorrect) {
        double averageScore = ((double) (correctFirst * 2 + correctSecond * 1)) / gamesPlayed;

        // Find the highest average score in existing scores
        double highestAverage = 0.0;
        LocalDateTime highScoreTime = null;
        for (Score score : scores) {
            double avg = (double) score.getScore() / score.numGamesPlayed;
            if (avg > highestAverage) {
                highestAverage = avg;
                highScoreTime = score.dateTimePlayed;
            }
        }

        if (averageScore > highestAverage) {
            System.out.println(String.format(
                    "CONGRATULATIONS! You are the new high score with an average of %.2f points per game; the previous record was %.2f points per game on %s.",
                    averageScore, highestAverage, highScoreTime.format(formatter)
            ));
        } else {
            System.out.println(String.format(
                    "You did not beat the high score of %.2f points per game from %s.",
                    highestAverage, highScoreTime.format(formatter)
            ));
        }
    }

    // Inner class to hold game result
    private static class GameResult {
        int correctFirst;
        int correctSecond;
        int incorrect;

        public GameResult(int correctFirst, int correctSecond, int incorrect) {
            this.correctFirst = correctFirst;
            this.correctSecond = correctSecond;
            this.incorrect = incorrect;
        }
    }
}
