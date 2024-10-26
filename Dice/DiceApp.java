import java.lang.Math;
import java.util.Scanner;

class DiceApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dice dice1 = new Dice();
        Dice dice2 = new Dice();
       
        
        System.out.println("Press <key> to roll the first dice");
        String roll = sc.nextLine();
        dice1.setDiceValue();
        dice1.printDiceValue();
        
        System.out.println("Press <key> to roll the second dice");
        roll = sc.nextLine();
        dice2.setDiceValue();
        dice2.printDiceValue();
        
        int sum = dice1.getDiceValue() + dice2.getDiceValue();
        System.out.print("Your total number is: " + sum);
    }
    
}

