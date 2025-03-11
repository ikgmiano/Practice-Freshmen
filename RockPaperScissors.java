import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    public static void main(String[] args) {
        System.out.println("Enter 0 to exit the program.");
        while (true) {
            System.out.print("what is your move? (1-Rock, 2-Paper, 3-Scissors): ");
            int choice = scanner.nextInt() - 1;
            if (choice == -1) {
                break;
            }
            int opponentChoice = random.nextInt(3);

            display("Player", choice);
            System.out.println();
            display("Computer", opponentChoice);

            if (choice == (opponentChoice + 1) % 3) {
                System.out.println("You won! Congratulations.");
            } else if (choice == (opponentChoice - 1 + 3) % 3) {
                System.out.println("You lost. Better luck next time!");
            } else {
                System.out.println("Draw!");
            }
            System.out.println();
        }
    }

    public static void display(String player, int move) {
        System.out.print(player + " chose ");
        switch (move) {
            case 0 -> displayRock();
            case 1 -> displayPaper();
            case 2 -> displayScissor();
        }
    }

    public static void displayRock() {
        System.out.println("rock");
        System.out.println(
               "_________\n" +
                "___)   ____)\n" +
                "      (_____)\n" +
                "      (_____)\n" +
                "      (____)\n" +
                "---.__(___)\n"
        );
    }
        
    public static void displayPaper() {
        System.out.println("paper");
        System.out.println(
                "____________\n" +
                "---'    ____)____\n" +
                "           ______)\n" +
                "          _______)\n" +
                "         _______)\n" +
                "---.__________)\n"
        );
    }

    public static void displayScissor() {
        System.out.println("scissors");
        System.out.println(
                "_______\n" +
                "---'   ____)____\n" +
                "          ______)\n" +
                "       __________)\n" +
                "      (____)\n" +
                "---.__(___)\n"
        );
    }
}