package ca.bcit.comp1510.lab08;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * The Games class is going to store the information of all the games
 * I tried some interesting and perhaps experimental code to simplify.
 *
 * @author Xing Jiarui
 * @version 0.1.0
 */
public class Games {
    /**
     * The score
     */
    private int score;

    /**
     * Check if it is a first play
     */
    private boolean firstPlay;

    /**
     * Initialise the scanner object
     */
    private final Scanner input = new Scanner(System.in);

    /**
     * Initialise the random object
     */
    private final Random random = new Random();

    /**
     * Constructor
     */
    public Games() {
        firstPlay = true;
    }

    /**
     * To encapsulate the welcome message
     */
    private void welcome() {
        if (firstPlay) {
            System.out.print("Welcome to the Games program! Make your choice (enter a number):");
            firstPlay = false;
        }
        System.out.print("""
                Games:
                1.	See your score
                2.	Guess a number
                3.	Play Rock, Paper, Scissors
                4.	Quit
                    """);
        System.out.print("> ");
    }

    /**
     * To play the games
     */
    public void play() {
        while (true) {
            welcome();
            // Check if the input is valid, if input is string, make it less than 1 and go to default
            int choice = input.hasNextInt() ? input.nextInt() : input.next().charAt(0) - 127;
            switch (choice) {
                case 1:
                    System.out.println("Your score is " + score);
                    break;
                case 2:
                    System.out.println("Loading: Guess a number...");
                    guessANumber();
                    break;
                case 3:
                    System.out.println("Loading: Rock, Paper, Scissors...");
                    rockPaperScissors();
                    break;
                case 4:
                    System.out.println("Thanks for playing!");
                    input.close();
                    return;
                default:
                    System.out.println("Wrong input! Try again.");
            }
        }
    }

    /**
     * A game to guess a number
     */
    public void guessANumber() {
        // Initialise
        System.out.println("Load success!\nGuess a number between 1 and 100");
        int number = random.nextInt(100) + 1, times = 0, guess = 0;

        // Play
        while (guess != number) {
            System.out.print("Your guess: ");
            // Get the input, check if the input is valid
            guess = input.hasNextInt() ? input.nextInt() : input.next().charAt(0) - 127;
            // Check if the input is valid
            if (guess < 1 || guess > 100) {
                System.out.println("Wrong input! Try again.");
                continue;
            }
            // Check if the input is correct
            times++;
            System.out.println(guess != number ? guess < number ? "Too low!" :
                    "Too high!" : "Correct! It was " + number + ".");
        }

        // Calculate the score
        // Check the singular and plural forms of "guess".
        String msg = times == 1 ? times + " guess" : times + " guesses";
        if (times <= 5) {
            System.out.println("Excellent! You got it in only " + msg +
                    "! You will get 5 points!");
            score += 5;
        } else {
            System.out.println("Sorry, you lose. You got it in " + msg +
                    ". You will get 0 points.");
        }
    }

    /**
     * A game to play Rock, Paper, Scissors
     */
    public void rockPaperScissors() {
        // Initialise
        final String[] choices = {"rock", "paper", "scissors"};
        System.out.println("Load success!\nRock, Paper, Scissors");
        /*cp here refers to "computer" rather than "couple", or, in a sense, it is my couple, hhh*/
        int cpChoiceNum = random.nextInt(3);
        // Get the string of computer's choice
        String cpChoice = choices[cpChoiceNum], userChoice;
        System.out.println("Your choice:");

        // Check if the input is valid
        do {
            System.out.println("Input \"rock\", \"paper\", or \"scissors\"");
            userChoice = input.next().toLowerCase().trim();
        } while (!Arrays.asList(choices).contains(userChoice));

        // Get the number of user's choice
        int userChoiceNum = Arrays.asList(choices).indexOf(userChoice);

        // Play
        switch (rockPaperScissorsRule(userChoiceNum, cpChoiceNum)) {
            case 0:
                System.out.println("Computer: " + cpChoice + "\nTie, No points!");
                break;
            case 1:
                System.out.println("Computer: " + cpChoice + "\nYou win, +5 points!");
                score += 5;
                break;
            case 2:
                System.out.println("Computer: " + cpChoice + "\nYou lose, -3 points!");
                score -= 3;
                break;
        }
    }

    /**
     * The rule of 剪刀石头布
     * <br>
     * Notice:
     * Choice: 0 -> rock, 1 -> paper, 2 -> scissors
     * Return: 0 -> tie, 1 -> win, 2 -> lose
     *
     * @param userChoiceNum the number of user's choice
     * @param cpChoiceNum   the number of computer's choice
     * @return 0 -> tie, 1 -> win, 2 -> lose
     */
    private int rockPaperScissorsRule(int userChoiceNum, int cpChoiceNum) {
        return switch (userChoiceNum) {
            // rock == rock ? tie : (rock == paper ? lose : win)
            case 0 -> (cpChoiceNum == 0) ? 0 : ((cpChoiceNum == 1) ? 2 : 1);
            case 1 -> (cpChoiceNum == 1) ? 0 : ((cpChoiceNum == 2) ? 2 : 1);
            case 2 -> (cpChoiceNum == 2) ? 0 : ((cpChoiceNum == 0) ? 2 : 1);
            default -> throw new IllegalArgumentException("Invalid choice: " + userChoiceNum);
        };
    }
}