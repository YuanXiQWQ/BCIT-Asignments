// NumberGame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NumberGame {
    private final JFrame frame;
    private final JButton[] buttons;
    private List<Integer> numbers;
    private int currentNumberIndex;
    private int successfulPlacements;
    private int totalGames;
    private int lostGames;

    public NumberGame() {
        frame = new JFrame("Number Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(4, 5));

        buttons = new JButton[20];
        for (int i = 0; i < 20; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
            final int index = i;
            buttons[i].addActionListener(e -> handleButtonClick(index));
            frame.add(buttons[i]);
        }

        initializeGame();
    }

    private void initializeGame() {
        numbers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            numbers.add(new Random().nextInt(1000) + 1);
        }
        Collections.shuffle(numbers);
        currentNumberIndex = 0;
        successfulPlacements = 0;
        totalGames += 1;
        showNextNumber();
    }

    private void showNextNumber() {
        if (currentNumberIndex < numbers.size()) {
            int number = numbers.get(currentNumberIndex);
            frame.setTitle("Number Game - Place the number: " + number);
        } else {
            // User has placed all numbers correctly
            successfulPlacements += 20;
            JOptionPane.showMessageDialog(frame, "Congratulations! You won the game.");
            askRetryOrQuit();
        }
    }

    private void handleButtonClick(int index) {
        if (currentNumberIndex >= numbers.size()) {
            return; // Game already won
        }

        JButton button = buttons[index];
        if (!button.getText().isEmpty()) {
            // Already placed, ignore
            return;
        }

        int number = numbers.get(currentNumberIndex);
        button.setText(String.valueOf(number));
        currentNumberIndex++;

        if (isGridInOrder()) {
            showNextNumber();
        } else {
            // User lost
            JOptionPane.showMessageDialog(frame, "You lost! The numbers are not in ascending order.");
            lostGames += 1;
            askRetryOrQuit();
        }
    }

    private boolean isGridInOrder() {
        List<Integer> placedNumbers = new ArrayList<>();
        for (JButton button : buttons) {
            if (!button.getText().isEmpty()) {
                placedNumbers.add(Integer.parseInt(button.getText()));
            }
        }
        for (int i = 1; i < placedNumbers.size(); i++) {
            if (placedNumbers.get(i) < placedNumbers.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    private void askRetryOrQuit() {
        int response = JOptionPane.showConfirmDialog(frame, "Do you want to try again?", "Retry", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            resetButtons();
            initializeGame();
        } else {
            // Show score and quit
            double average = (double) successfulPlacements / totalGames;
            String message = String.format("You lost %d out of %d games, with %d successful placements, an average of %.2f per game.",
                    lostGames, totalGames, successfulPlacements, average);
            JOptionPane.showMessageDialog(frame, message);
            frame.dispose();
        }
    }

    private void resetButtons() {
        for (JButton button : buttons) {
            button.setText("");
        }
    }

    public void start() {
        frame.setVisible(true);
    }
}
