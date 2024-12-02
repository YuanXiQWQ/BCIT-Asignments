import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

/**
 * The main application class for the Number Game. Handles the user interface and game
 * logic.
 *
 * @author Jiarui Xing
 */
public class NumberGameApp extends Application {
    private static NumberGame gameInstance;
    private Stage primaryStage;

    private static final int TOTAL_NUMBERS = 20;
    private static final int ROWS = 4;
    private static final int COLS = 5;
    private List<Integer> numbers;
    private int currentNumberIndex;
    private int successfulPlacements;
    private Button[][] buttons;
    private int[][] gridValues;
    private Label statusLabel;
    private Label currentNumberLabel;
    private boolean gameOver;
    private int currentNumber;

    /**
     * Launches the NumberGame application.
     *
     * @param game The NumberGame instance.
     */
    public static void launchApp(final NumberGame game)
    {
        gameInstance = game;
        Application.launch();
    }

    /**
     * Starts the NumberGame application.
     *
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(final Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Number Game");
        setupGame();
    }

    /**
     * Sets up the initial game state and user interface.
     */
    private void setupGame()
    {
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        buttons = new Button[ROWS][COLS];
        gridValues = new int[ROWS][COLS];

        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                final Button btn = new Button("");
                btn.setPrefSize(60, 60);
                final int row = i;
                final int col = j;
                btn.setOnAction(e -> handleButtonClick(row, col));
                buttons[i][j] = btn;
                grid.add(btn, j, i);
            }
        }

        numbers = generateRandomNumbers();
        currentNumberIndex = 0;
        successfulPlacements = 0;
        gameOver = false;
        currentNumber = numbers.get(currentNumberIndex);

        // Current number label
        currentNumberLabel = new Label("Current Number: " + currentNumber);
        currentNumberLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Status label
        statusLabel = new Label("Please select a cell to place the number.");
        statusLabel.setStyle("-fx-font-size: 14px;");

        // Layout
        final VBox topPane = new VBox(10, currentNumberLabel, statusLabel);
        topPane.setAlignment(Pos.CENTER);

        final VBox gridPane = new VBox(10, grid);
        gridPane.setAlignment(Pos.CENTER);

        final VBox rootPane = new VBox(20, topPane, gridPane);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setPadding(new javafx.geometry.Insets(20));

        final Scene scene = new Scene(rootPane, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Generates a list of 20 random numbers between 1 and 1000.
     *
     * @return List of random integers.
     */
    private List<Integer> generateRandomNumbers()
    {
        final List<Integer> nums = new ArrayList<>();
        final Random rand = new Random();
        for(int i = 0; i < TOTAL_NUMBERS; i++)
        {
            nums.add(rand.nextInt(1000) + 1);
        }
        // Shuffle the numbers to ensure randomness
        Collections.shuffle(nums);
        return nums;
    }

    /**
     * Handles the button click event to place the current number.
     *
     * @param row The row of the button clicked.
     * @param col The column of the button clicked.
     */
    private void handleButtonClick(final int row, final int col)
    {
        if(gameOver)
        {
            return;
        }

        if(gridValues[row][col] != 0)
        {
            showAlert(Alert.AlertType.WARNING, "Invalid Operation",
                    "This cell is already occupied. Please choose another cell.");
            return;
        }

        placeNumber(row, col);

        if(isPlacementValid())
        {
            gridValues[row][col] = currentNumber;
            buttons[row][col].setText(String.valueOf(currentNumber));
            buttons[row][col].setDisable(true);
            successfulPlacements++;

            // Proceed to next number
            currentNumberIndex++;
            if(currentNumberIndex < TOTAL_NUMBERS)
            {
                currentNumber = numbers.get(currentNumberIndex);
                currentNumberLabel.setText("Current Number: " + currentNumber);
                statusLabel.setText("Please select a cell to place the number.");
            } else
            {
                // All numbers placed successfully
                gameInstance.incrementGamesWon();
                gameInstance.addSuccessfulPlacements(successfulPlacements);
                showAlert(Alert.AlertType.INFORMATION, "Victory",
                        "Congratulations! You won!");
                gameOver = true;
                askPlayAgain();
            }
        } else
        {
            // Invalid placement
            gameInstance.incrementGamesLost();
            gameInstance.addSuccessfulPlacements(successfulPlacements);
            showAlert(Alert.AlertType.ERROR, "Defeat",
                    "Cannot place number " + currentNumber + " here. Game Over!");
            gameOver = true;
            askPlayAgain();
        }
    }

    /**
     * Places the number in the specified cell.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     */
    private void placeNumber(final int row, final int col)
    {
        gridValues[row][col] = currentNumber;
        buttons[row][col].setText(String.valueOf(currentNumber));
        buttons[row][col].setDisable(true);
    }

    /**
     * Checks if the current placement maintains the ascending order.
     *
     * @return True if placement is valid, else false.
     */
    private boolean isPlacementValid()
    {
        // Flatten the grid to a list in row-major order
        final List<Integer> placedNumbers = new ArrayList<>();
        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                if(gridValues[i][j] != 0)
                {
                    placedNumbers.add(gridValues[i][j]);
                }
            }
        }
        // Check if the list is sorted in ascending order
        for(int i = 1; i < placedNumbers.size(); i++)
        {
            if(placedNumbers.get(i) < placedNumbers.get(i - 1))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Prompts the user to play again or return to the main menu.
     */
    private void askPlayAgain()
    {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to play again?");

        final ButtonType buttonYes = new ButtonType("Yes");
        final ButtonType buttonNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        final Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == buttonYes)
        {
            startNewGame();
        } else
        {
            // Show statistics
            showStatistics();

            // Close the window and return to main menu
            primaryStage.close();
        }
    }

    /**
     * Starts a new game by resetting all game states.
     */
    private void startNewGame()
    {
        // Reset grid values and buttons
        for(int i = 0; i < ROWS; i++)
        {
            Arrays.fill(gridValues[i], 0);
            for(int j = 0; j < COLS; j++)
            {
                buttons[i][j].setText("");
                buttons[i][j].setDisable(false);
            }
        }
        numbers = generateRandomNumbers();
        currentNumberIndex = 0;
        successfulPlacements = 0;
        gameOver = false;
        currentNumber = numbers.get(currentNumberIndex);
        currentNumberLabel.setText("Current Number: " + currentNumber);
        statusLabel.setText("Please select a cell to place the number.");
    }

    /**
     * Displays the game statistics to the user.
     */
    private void showStatistics()
    {
        final int gamesWon = gameInstance.getGamesWon();
        final int gamesLost = gameInstance.getGamesLost();
        final int totalPlacements = gameInstance.getTotalSuccessfulPlacements();
        final int gamesPlayed = gamesWon + gamesLost;
        final double averagePlacements = gamesPlayed > 0
                                         ? (double) totalPlacements / gamesPlayed
                                         : 0;

        final StringBuilder sb;
        sb = new StringBuilder();
        sb.append("Game Statistics:")
                .append("\nTotal Games Played: ").append(gamesPlayed)
                .append("\nGames Won: ").append(gamesWon)
                .append("\nGames Lost: ").append(gamesLost)
                .append("\nTotal Successful Placements: ").append(totalPlacements)
                .append("\nAverage Successful Placements per Game: ")
                .append(String.format("%.2f", averagePlacements));

        final Alert statsAlert = new Alert(Alert.AlertType.INFORMATION);
        statsAlert.setTitle("Statistics");
        statsAlert.setHeaderText(null);
        statsAlert.setContentText(sb.toString());
        statsAlert.showAndWait();
    }

    /**
     * Displays an alert dialog with the specified parameters.
     *
     * @param type    The type of alert.
     * @param title   The title of the alert.
     * @param content The content message of the alert.
     */
    private void showAlert(final Alert.AlertType type, final String title,
                           final String content)
    {
        final Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
