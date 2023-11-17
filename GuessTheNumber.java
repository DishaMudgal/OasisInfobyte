package OASIS;
import java.util.Random;
import java.util.Scanner;
public class GuessTheNumber {

	            public static void main(String[] args) {
		        Scanner scanner = new Scanner(System.in);
		        Random random = new Random();

		        int totalRounds = 3; 
		        int attemptsPerRound = 3; 
		        int score = 0;

		        for (int round = 1; round <= totalRounds; round++) {
		            int generatedNumber = random.nextInt(100);
		            System.out.println("\nRound " + round);

		            for (int attempt = 1; attempt <= attemptsPerRound; attempt++) {
		                System.out.print("Attempt " + attempt + ": Guess the number (between 1 and 100): ");
		                int userGuess = scanner.nextInt();

		                if (userGuess == generatedNumber)
		                {
		                    System.out.println("congratulations You guessed the correct number");
		                    score += attemptsPerRound - (attempt - 1);
		                    break;
		                } else if (userGuess < generatedNumber) {
		                    System.out.println("lower. Try again.");
		                } else {
		                    System.out.println("higher. Try again.");
		                }

		                if (attempt == attemptsPerRound) {
		                    System.out.println("Sorry, you've run out of attempts. The correct number was: " + generatedNumber);
		                }
		            }

		            if (round < totalRounds) {
		                System.out.print("Do you want to play another round? (yes/no): ");
		                String playAgain = scanner.next().toLowerCase();
		                if (!playAgain.equals("yes")) {
		                    break;
		                }
		            }
		        }

		        System.out.println("\nGAME OVER!");
		        System.out.println("YOUR TOTAL SCORE IS:" + score);
		       
	            }
}