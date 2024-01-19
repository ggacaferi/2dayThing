import java.util.Scanner;
import java.util.InputMismatchException;

public class Main{
    public static void main(String[] args) {
        // flip a coin
        String msg = "Player, please choose if you want heads or tails. Press 0 for heads, or 1 for tails";
        int choice = -1;
        do {
            choice = input(msg);
        } while (choice != 0 && choice != 1);

        // Check if player 1 plays first or not
        double rand = Math.random();
        String first;
        if ((rand < 0.5 && choice == 0) || (rand > 0.5 && choice == 1)) {
            System.out.println("White goes first because he guessed correctly");
            first = "white";
        } else {
            System.out.println("Black goes first because white guessed incorrectly");
            first = "black";
        }

        // Initialize the board and armies
        int[] board = new int[16];
        int[] white = new int[8];
        int[] black = new int[8];
        fillArrayNumbers(white);
        fillArrayNumbers(black);

        // Deployment phase
        for (int i = 0; i < 8; i++) {
            System.out.println((first.equals("white") ? "White" : "Black") + " plays");
            deploy(first.equals("white") ? white : black, board);
            System.out.println((first.equals("white") ? "Black" : "White") + " plays");
            deploy(first.equals("white") ? black : white, board);
        }

        // Destruction phase
        while (canDestroy(white, board) || canDestroy(black, board)) {
            if (first.equals("white") && canDestroy(white, board)) {
                System.out.println("White's turn to destroy");
                destroy(white, black, board);
            }
            if (first.equals("black") && canDestroy(black, board)) {
                System.out.println("Black's turn to destroy");
                destroy(black, white, board);
            }
        }

        // Determine the winner
        int whiteCount = countRemaining(white, board);
        int blackCount = countRemaining(black, board);
        if (whiteCount > blackCount) {
            System.out.println("White wins!");
        } else if (blackCount > whiteCount) {
            System.out.println("Black wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    public static void deploy(int[] player, int[] board) {
        printBoard(board);
        int num = getNum(player, "Select a number to deploy");
        int position;
        do {
            position = input("Choose a position (1-16)") - 1;
        } while (position < 0 || position >= board.length || board[position] != 0);
        board[position] = num;
        player[num - 1] = 0;
    }

    public static void destroy(int[] player, int[] opponent, int[] board) {
        int num;
        int pos;
        boolean destroyed = false;
        do {
            num = input("Choose an opponent's number to destroy");
            pos = indexOf(board, num);
            if (pos != -1 && isDestroyable(player, board, pos)) {
                board[pos] = 0;
                destroyed = true;
            } else {
                System.out.println("Cannot destroy this number. Choose another.");
            }
        } while (!destroyed);
    }

    public static boolean isDestroyable(int[] player, int[] board, int pos) {
        int num = board[pos];
        return (pos > 0 && playerContains(player, board[pos - 1]) && board[pos - 1] > num) ||
               (pos < board.length - 1 && playerContains(player, board[pos + 1]) && board[pos + 1] > num) ||
               (pos > 0 && pos < board.length - 1 && playerContains(player, board[pos - 1]) && playerContains(player, board[pos + 1]) && 
               (board[pos - 1] + board[pos + 1]) > num);
    }

    public static boolean canDestroy(int[] player, int[] board) {
        for (int num : player) {
            int pos = indexOf(board, num);
            if (pos != -1 && isDestroyable(player, board, pos)) {
                return true;
            }
        }
        return false;
    }

    public static int countRemaining(int[] player, int[] board) {
        int count = 0;
        for (int num : player) {
            if (indexOf(board, num) != -1) {
                count++;
            }
        }
        return count;
    }

    public static int input(String msg) {
        Scanner scan = new Scanner(System.in);
        int num;
        System.out.print(msg + ": ");
        try {
            num = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("ERROR: Invalid input. Please enter a number.");
            scan.nextLine(); // clear the buffer
            return input(msg);
        }
        return num;
    }

    public static int getNum(int[] arr, String msg) {
        int num;
        do {
            num = input(msg);
        } while (!playerContains(arr, num));
        return num;
    }

    public static boolean playerContains(int[] player, int num) {
        for (int value : player) {
            if (value == num) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static void printBoard(int[] board) {
        for (int i = 0; i < board.length; i++) {
            System.out.print("[" + (board[i] == 0 ? " " : board[i]) + "] ");
        }
        System.out.println();
    }

    public static void fillArrayNumbers(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
    }
}
