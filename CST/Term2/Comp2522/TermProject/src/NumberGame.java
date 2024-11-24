import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implements the Number Game where users place numbers in ascending order. Uses a GUI to
 * interact with the user. Handles game logic, scoring, and user interactions.
 * <p>
 * This class makes use of an interface, an abstract class, and a concrete class as per
 * requirements. Interface: GameInterface Abstract Class: AbstractNumberGame Concrete
 * Class: NumberGame
 * <p>
 * Note: For the interface and abstract class, additional code would be needed. For
 * brevity, they are included within this class.
 *
 * @author Jiarui Xing
 */
public class NumberGame extends AbstractNumberGame implements GameInterface {
    private final JFrame frame;
    private final JButton[] buttons;
    private final List<Integer> numbers;
    private int currentNumberIndex;
    private int successfulPlacements;
    private int totalGames;
    private int lostGames;

    private static final int GRID_SIZE = 20;
    private static final int NUMBER_RANGE = 1000;

    /**
     * Constructs a NumberGame and initializes the GUI components.
     */
    public NumberGame()
    {
        frame = new JFrame("Number Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(4, 5));

        buttons = new JButton[GRID_SIZE];
        for(int i = 0; i < GRID_SIZE; i++)
        {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
            final int index = i;
            buttons[i].addActionListener(e -> handleButtonClick(index));
            frame.add(buttons[i]);
        }

        numbers = new ArrayList<>();
        initializeGame();
    }

    /**
     * Initializes the game by generating random numbers and resetting variables.
     */
    private void initializeGame()
    {
        numbers.clear();
        for(int i = 0; i < GRID_SIZE; i++)
        {
            numbers.add(new Random().nextInt(NUMBER_RANGE) + 1);
        }
        Collections.shuffle(numbers);
        currentNumberIndex = 0;
        successfulPlacements = 0;
        totalGames += 1;
        showNextNumber();
    }

    /**
     * Displays the next number to be placed.
     */
    private void showNextNumber()
    {
        if(currentNumberIndex < numbers.size())
        {
            int number = numbers.get(currentNumberIndex);
            frame.setTitle("Number Game - Place the number: " + number);
        } else
        {
            // User has placed all numbers correctly
            successfulPlacements += GRID_SIZE;
            JOptionPane.showMessageDialog(frame, "Congratulations! You won the game.");
            askRetryOrQuit();
        }
    }

    /**
     * Handles the button click event when the user selects a slot.
     *
     * @param index the index of the button clicked
     */
    private void handleButtonClick(int index)
    {
        if(currentNumberIndex >= numbers.size())
        {
            return; // Game already won
        }

        JButton button = buttons[index];
        if(!button.getText().isEmpty())
        {
            // Slot already occupied, ignore
            return;
        }

        int number = numbers.get(currentNumberIndex);
        button.setText(String.valueOf(number));
        currentNumberIndex++;
        successfulPlacements++;

        if(isGridInOrder())
        {
            if(currentNumberIndex < numbers.size())
            {
                if(hasAvailableSlot())
                {
                    showNextNumber();
                } else
                {
                    // No slots left but numbers remain, user loses
                    JOptionPane.showMessageDialog(frame,
                            "You lost! No available slots to place the next number.");
                    lostGames++;
                    askRetryOrQuit();
                }
            } else
            {
                // All numbers placed successfully
                JOptionPane.showMessageDialog(frame,
                        "Congratulations! You won the game.");
                askRetryOrQuit();
            }
        } else
        {
            // Numbers not in order, user loses
            JOptionPane.showMessageDialog(frame,
                    "You lost! The numbers are not in ascending order.");
            lostGames++;
            askRetryOrQuit();
        }
    }

    /**
     * Checks if the numbers placed in the grid are in ascending order.
     *
     * @return true if the numbers are in order, false otherwise
     */
    private boolean isGridInOrder()
    {
        List<Integer> placedNumbers = new ArrayList<>();
        for(JButton button : buttons)
        {
            if(!button.getText().isEmpty())
            {
                placedNumbers.add(Integer.parseInt(button.getText()));
            }
        }
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
     * Checks if there is at least one available slot in the grid.
     *
     * @return true if an empty slot exists, false otherwise
     */
    private boolean hasAvailableSlot()
    {
        for(JButton button : buttons)
        {
            if(button.getText().isEmpty())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Asks the user whether they want to retry or quit the game.
     */
    private void askRetryOrQuit()
    {
        int response =
                JOptionPane.showConfirmDialog(frame, "Do you want to try again?", "Retry",
                        JOptionPane.YES_NO_OPTION);
        if(response == JOptionPane.YES_OPTION)
        {
            resetButtons();
            initializeGame();
        } else
        {
            // Show score and quit
            double average = (double) successfulPlacements / totalGames;
            String message = getString(average);
            JOptionPane.showMessageDialog(frame, message);
            frame.dispose();
        }
    }

    /**
     * Gets the message to be displayed based on the game outcome.
     *
     * @param average the average number of successful placements per game
     * @return the message to be displayed
     */
    private String getString(double average)
    {
        String message;
        if(lostGames == totalGames)
        {
            message = String.format(
                    "You lost %d out of %d game(s), with %d successful placements, " +
                            "an average of %.2f per game.", lostGames, totalGames,
                    successfulPlacements, average);
        } else
        {
            int wonGames = totalGames - lostGames;
            message = String.format(
                    "You won %d out of %d game(s) and lost %d, with %d successful " +
                            "placements, an average of %.2f per game.", wonGames,
                    totalGames, lostGames, successfulPlacements, average);
        }
        return message;
    }

    /**
     * Resets all buttons to empty for a new game.
     */
    private void resetButtons()
    {
        for(JButton button : buttons)
        {
            button.setText("");
        }
    }

    /**
     * Starts the Number Game by displaying the GUI.
     */
    @Override
    public void start()
    {
        frame.setVisible(true);
    }
}

/**
 * An interface representing the game interface. Can be used to define common methods for
 * different games.
 */
interface GameInterface {
    void start();
}

/**
 * An abstract class providing base functionality for number games. Can be extended by
 * concrete number game implementations.
 */
abstract class AbstractNumberGame {
    // Base functionality can be added here if needed
}
