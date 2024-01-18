import java.util.Scanner;
import java.util.Random;

public class DeployAndDestroy{
    public static void main(String[] args){
     // flip a coin
        Scanner scan = new Scanner(System.in);
        System.out.println("Player 1, please choose if you want heads or tails. Press 0 for heads, or 1 for tails");
        int choice = checkInput(0, 1);
        if (Math.random() < 0.5 && choice == 0){
            System.out.println("Player 1 goes first because he guessed correctly");
        }
        else if (Math.random() > 0.5 && choice == 1){
            System.out.println("Player 1 goes first because he guessed correctly");
        }else{
            System.out.println("Player 2 goes first because player 1 guessed incorrectly");
        }
        scan.close();
        int[] table = new int[16];
        int [] white = new int[8];
        int[] black = new int [8];
        fillArrayNumbers(white);
        fillArrayNumbers(black);
        System.out.println(white);

        
    }
        

           
    


/**
 * @param input1
 * @param input2
 * @return
 */
public static  int checkInput(int input1, int input2){
    Scanner scan = new Scanner(System.in);
    int value;
    do {
        System.out.println("Please enter 0 or 1");
        value = scan.nextInt();

    } while (value !=input1 && value != input2);
    scan.close();
    return value;



}


public static int[] fillArrayNumbers(int array[]){
    Scanner scan = new Scanner(System.in); 
    for (int i=0; i < 8 ; i++) {
        array[i] = i+1 ; 
        
    }
    scan.close();
    return array;
}
}


// 2 inputs

// empty array


// ask which number

// ask where to put it

// if surrounded by 2 values that are greater than the one in the middle, the one in the middle is taken out

// if next to a greater value, the smaller one is taken out