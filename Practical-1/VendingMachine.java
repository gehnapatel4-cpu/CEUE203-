import java.util.Scanner;

// Enum for coin value
enum Coin {
    ONE, TWO, FIVE, TEN;
}

public class VendingMachine {
    public static void main(String[] args) {
        int price = 15;
        int total = 0;

        Scanner sc = new Scanner(System.in);
        System.out.println("Snack price: 15. Enter coins (ONE, TWO, FIVE, TEN):");

        // Loop for taking coins until total reaches 15 or more
        while (total < price) {
            String input = sc.next().toUpperCase();
            Coin coin = Coin.valueOf(input);

            // Use switch expression to convert Coin to value
            int value = switch (coin) {
                case ONE -> 1;
                case TWO -> 2;
                case FIVE -> 5;
                case TEN -> 10;
            };

            total += value;
            System.out.println("Total so far: " + total);
        }

        // Final output
        System.out.println("Paid. Change: " + (total - price));
        sc.close();
    }
}
