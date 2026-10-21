import java.util.Random;
import java.util.Scanner;

public class Main {

   
    enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    // Method to decide the winner
    public static int winner(Move a, Move b) {

        if (a == b) {
            return 0;
        }

        switch (a) {

            case ROCK:
                if (b == Move.SCISSORS || b == Move.LIZARD)
                    return 1;
                else
                    return -1;

            case PAPER:
                if (b == Move.ROCK || b == Move.SPOCK)
                    return 1;
                else
                    return -1;

            case SCISSORS:
                if (b == Move.PAPER || b == Move.LIZARD)
                    return 1;
                else
                    return -1;

            case LIZARD:
                if (b == Move.SPOCK || b == Move.PAPER)
                    return 1;
                else
                    return -1;

            case SPOCK:
                if (b == Move.SCISSORS || b == Move.ROCK)
                    return 1;
                else
                    return -1;
        }

        return 0;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        Move[] moves = Move.values();

        int playerScore = 0;
        int computerScore = 0;

        for (int i = 1; i <= 5; i++) {

            System.out.println("\nRound " + i);

            System.out.print("Enter your move (ROCK, PAPER, SCISSORS, LIZARD, SPOCK): ");

            Move player = Move.valueOf(sc.next().toUpperCase());

            Move computer = moves[random.nextInt(moves.length)];

            System.out.println("Computer chose: " + computer);

            int result = winner(player, computer);

            if (result == 1) {
                System.out.println("You win this round!");
                playerScore++;
            } else if (result == -1) {
                System.out.println("Computer wins this round!");
                computerScore++;
            } else {
                System.out.println("Round is a tie!");
            }
        }

        System.out.println("\nFinal Score");
        System.out.println("You: " + playerScore);
        System.out.println("Computer: " + computerScore);

        if (playerScore > computerScore) {
            System.out.println("You win " + playerScore + "-" + computerScore);
        } else if (computerScore > playerScore) {
            System.out.println("Computer wins " + computerScore + "-" + playerScore);
        } else {
            System.out.println("Match Draw!");
        }

        sc.close();
    }
}
