import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    private static final int MIN = 1;
    private static final int MAX = 100;
    private static final int MAX_ATTEMPTS = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        int totalScore = 0;
        int roundsPlayed = 0;

        while (playAgain) {
            int numberToGuess = random.nextInt(MAX - MIN + 1) + MIN;
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("I have selected a number between " + MIN + " and " + MAX + ". Can you guess it?");
            System.out.println("You have " + MAX_ATTEMPTS + " attempts.");

            while (attempts < MAX_ATTEMPTS && !guessedCorrectly) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == numberToGuess) {
                    guessedCorrectly = true;
                    System.out.println("Congratulations! You've guessed the number in " + attempts + " attempts.");
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low. Try again.");
                } else {
                    System.out.println("Too high. Try again.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Sorry, you've used all " + MAX_ATTEMPTS + " attempts. The number was: " + numberToGuess);
            }

            totalScore += MAX_ATTEMPTS - attempts + 1;
            roundsPlayed++;

            System.out.print("Do you want to play another round? (yes/no): ");
            scanner.nextLine(); // Consume newline
            String response = scanner.nextLine();
            playAgain = response.equalsIgnoreCase("yes");
        }

        System.out.println("You've played " + roundsPlayed + " rounds.");
        System.out.println("Your total score is: " + totalScore);
        System.out.println("Thank you for playing!");

        scanner.close();
    }
}

