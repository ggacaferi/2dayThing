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
        int[] board = new int[16];
        int[] white = new int[8];
        int[] whitePositions = new int[8];
        int[] black = new int [8];
        int[] blackPositions = new int [8];
        fillArrayNumbers(white);
        fillArrayNumbers(whitePositions);
        fillArrayNumbers(black);
        fillArrayNumbers(blackPositions);

        // Deploy all the numbers
        for (int i = 0; i < white.length; i++) {
            if (first == "white") {
                System.out.println("White: ");
                deploy(white, board, whitePositions, i);
                
                System.out.println("Black");
                deploy(black, board, blackPositions, i);
            } else {
                // If the black is first then reverse the order
                System.out.println("Black");
                deploy(black, board, whitePositions, i);
                
                System.out.println("White");
                deploy(white, board, blackPositions, i);
            }
        }

        // Destroy
        Boolean battleFinished = false;
        int[] sortedWhitePositions = whitePositions;
        int[] sortedWhite = white;
        int[] sortedBlackPositions = blackPositions;
        int[] sortedBlack = black;
        int[] availableForWhite = new int[4];
        int[] availableForBlack = new int[4];
        fillArrayNumbers(availableForWhite);
        fillArrayNumbers(availableForBlack);

        // Sort the positions and the nums in descending order
        sort(sortedWhitePositions, white);
        sort(sortedBlackPositions, black);

        while (!battleFinished) {
            if (first == "white") {
                destroy(sortedWhite, sortedWhitePositions, board);
                destroy(sortedBlack, sortedBlackPositions, board);
            } else {
                destroy(sortedBlack, sortedBlackPositions, board);
                destroy(sortedWhite, sortedWhitePositions, board);
            }
        }
    }

    public static void destroy(int player[], int positions[], int board[]){ 
         
    }

    public static int[] findAvailablePositions(int available[], int num[] , int numPositions[] ) {
        int[] availablePositions = new int[4];
        for (int i = 0; i < available.length; i++){
            for (int j = 0; j < numPositions.length; j++){
                if (available[i] == num[j]) {
                    available[i]= numPositions[j];
                }
            }
        }
        return availablePositions;
    }


    public static int[] findAvailable(int whiteNums[], int blackNums[], int whitePositions[], int blackPositions[]) {
        int[] available = new int[4];
        int index = 0;
        // Loop through the the sorted positions
        for (int i = 0; i < whitePositions.length-1; i++) {
            // If the current position and the one next to it have a difference of two, it means they surround an opponent troop
            if ((whitePositions[i] - whitePositions[i+1]) == 2) {
                // And if the sum of them is greater than the opponent's number value then this value is available for elimination
                if ((whiteNums[whitePositions[i]] + whiteNums[whitePositions[i+1]]) > blackNums[blackPositions[i]]) {
                    available[index] = blackNums[blackPositions[i]];
                    index++;
                }
            }
        }
        return available;
    }

    public static void sort(int positions[], int nums[]) {
        for (int i = 0; i < positions.length-1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < positions.length; j++) {
                if (positions[j] > positions[maxIndex]) {
                    maxIndex = j;
                }
            }

            // Swap
            if (i != maxIndex) {
                int tempPos = positions[maxIndex];
                positions[maxIndex] = positions[i];
                positions[i] = tempPos;

                int tempNums = nums[maxIndex];
                nums[maxIndex] = nums[i];
                nums[i] = tempNums;
            }
        }
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