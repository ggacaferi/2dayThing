import java.util.Scanner;
import java.util.InputMismatchException;

public class DeployAndDestroy{
    public static void main(String[] args){
        // flip a coin
        String msg = "Player, please choose if you want heads or tails. Press 0 for heads, or 1 for tails";
        int choice = -1;
        do {
            choice = input(msg);
        } while(choice != 0 && choice != 1);

        // Check if the player 1 plays first or not
        double rand = Math.random();
        String first;
        if ((rand < 0.5 && choice == 0) || (rand > 0.5 && choice == 1)) {
            System.out.println("White goes first because he guessed correctly");
            first = "white";
        } else {
            System.out.println("Black goes first because white guessed incorrectly");
            first = "black";
        }

        // Declare the arrays and fill them with 0s
        int[] board = new int[8];
        int[] white = new int[4];
        int[] whitePositions = new int[4];
        int[] black = new int [4];
        int[] blackPositions = new int [4];
        fillArrayNumbers(white);
        fillArrayNumbers(whitePositions);
        fillArrayNumbers(black);
        fillArrayNumbers(blackPositions);

        // Deploy all the numbers
        for (int i = 0; i < white.length; i++) {
            if (first=="white") {
                System.out.println("White: ");
                deploy(white, board, whitePositions, i);
                
                System.out.println("Black");
                deploy(black, board, blackPositions, i);
            } else if(first=="black"){
                // If the black is first then reverse the order
                System.out.println("Black");
                deploy(black, board, whitePositions, i);
                
                System.out.println("White");
                deploy(white, board, blackPositions, i);
            }
        }

        // Destroy
        Boolean battleFinished = false;
        int[] sortedWhitePositions = sort(whitePositions);
        int[] sortedWhite = sort(white);
        int[] sortedBlackPositions = sort(blackPositions);
        int[] sortedBlack = sort(black);

        while (!battleFinished) {
            if (first == "white") {
                System.out.println("White: ");
                destroy(sortedWhite, sortedWhitePositions, board);

                System.out.println("Black: ");
                destroy(sortedBlack, sortedBlackPositions, board);
            } else {
                System.out.println("Black: ");
                destroy(sortedBlack, sortedBlackPositions, board);
                System.out.println("White: ");
                destroy(sortedWhite, sortedWhitePositions, board);
            }
        }
    }

    public static void destroy(int nums[], int positions[], int board[]){ 
        // Print board
        printBoard(board);

        int pos = -1;
        int num = -1;
        do {
            num = input("Give target");
            // Check if the number exists   
            for (int i = 0; i < positions.length; i++) {
                if (nums[i] == num) {
                    pos = positions[i];
                    break;
                }
            }    
        } while (pos != -1);

        // Find if there is an opponent number
        Boolean isLeftMine = false;
        Boolean isRightMine = false;
        if ((positions[pos-1] - positions[pos]) != 1) {
            isLeftMine = true;
        }
        if ((positions[pos+1] - positions[pos]) != 1) {
            isRightMine = true;
        }

        // Find the sum
        int sum = 0;
        if (isLeftMine) {
            sum += nums[pos-1];
        } else if (isRightMine) {
            sum += nums[pos+1];
        } else {
            sum += nums[pos-1] + nums[pos+1];
        }

        // Find if the target (num) is destroyable
        // If it is destroy it then the other player plays
        if (sum - num > 0) {
            board[pos] = 0;
        } else {
            System.out.println("You cannot destroy this number\nYou lost your turn...");
        }
    }

    public static int[] sort(int arr[]) {
        for (int i = 0; i < arr.length-1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = j;
                }
            }

            // Swap
            if (arr[i] != arr[maxIndex]) {
                int temp = arr[maxIndex];
                arr[maxIndex] = arr[i];
                arr[i] = temp;
            }
        }

        return arr;
    }


    public static void printBoard(int board[]) {
        for (int i = 0; i < board.length; i++) {
            System.out.print("[" + board[i] + "] ");
        }
        System.out.println("");
    }


    public static void deploy(int player[], int board[], int positions[], int index) {
        // Print the available numbers
        System.out.println("Available numbers:");
        for (int i = 0; i < player.length; i++) {
            System.out.print("[" + player[i] + "] ");
        }
        // New line
        System.out.println("");

        // Get the number
        int num = getNum(player, "Give number");

        // Get position and check if it exists
        printBoard(board);
        int position = -1;
        do{
            position = input("Give position (1-16)")-1;
        } while((position < 0 || position > board.length) || board[position] != 0);

        // Put the number in the board position
        board[position] = num;
        
        // Update the player array
        player[num-1] = 0;

        // Update the positions
        positions[index] = position;

        // New line
        System.out.println("\n");
    }

    public static int getNum(int arr[], String msg) {
        Boolean check;
        Boolean isZero;
        int num;
        do{
            check = true;
            isZero = false;
            num = input(msg);
            // Check if the number exists
            Boolean numFound = false;
            for (int j = 0; j < arr.length; j++) {
                if (num == arr[j]) {
                    numFound = true;
                } 
                
                if (arr[j] == 0) {
                    isZero = true;
                }
            }
            if (!numFound) {
                check = false;
            }
        } while (!check && isZero);

        return num;
    }
           

    public static int input(String msg){
        Scanner scan = new Scanner(System.in);
        int num = -1;
        System.out.print(msg + ": ");

        // Try to get the input if the user inputs something else than an int
        // Close the program
        try {
            num = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("ERROR: Invalid input\nClosing the program...");
            System.exit(1);
        }

        return num;
    }

    public static int[] fillArrayNumbers(int array[]){
        for (int i=0; i < array.length; i++) {
            array[i] = i+1;
        }
        return array;
    }
}