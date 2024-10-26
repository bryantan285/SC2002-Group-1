import java.util.Scanner;
    public class Lab2p1 {
        public static void main(String[] args)
        {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Perform the following methods:");
            System.out.println("1: miltiplication test");
            System.out.println("2: quotient using division by subtraction");
            System.out.println("3: remainder using division by subtraction");
            System.out.println("4: count the number of digits");
            System.out.println("5: position of a digit");
            System.out.println("6: extract all odd digits");
            System.out.println("7: quit");
            int m,n;
            choice = sc.nextInt();
            switch (choice) {
                case 1: 
                    Lab2p1_Methods.mulTest();
                    break;
                case 2:  
                    System.out.print("m = ");
                    m = sc.nextInt();
                    System.out.print("n = ");
                    n = sc.nextInt();
                    System.out.printf("%d/%d = %d\n", m,n,Lab2p1_Methods.divide(m, n));
                    break;

                case 3:
                    System.out.print("m = ");
                    m = sc.nextInt();
                    System.out.print("n = ");
                    n = sc.nextInt();
                    System.out.printf("%d %% %d = %d\n", m,n,Lab2p1_Methods.modulus(m,n));
                    break;
                case 4: 
                    System.out.print("n = ");
                    n = sc.nextInt();
                    if (Lab2p1_Methods.countDigits(n)<0) System.out.println("Error input!!");
                    else System.out.println("count = " + Lab2p1_Methods.countDigits(n));
                    break;
                case 5: 
                    System.out.print("n = ");
                    n = sc.nextInt();
                    System.out.print("digit = ");
                    m = sc.nextInt(); //NOTE m will take the digit parameter!!!!
                    if (Lab2p1_Methods.position(n, m) == 0) System.out.println("position = -1");
                    else System.out.printf("position = " + Lab2p1_Methods.position(n, m) + '\n');
                    break;
                case 6: 
                    System.out.print("n = ");
                    long num = sc.nextInt();
                    if (Lab2p1_Methods.extractOddDigits(num)==0) System.out.println("Error input!!");
                    else System.out.printf("oddDigits = " + Lab2p1_Methods.extractOddDigits(num) + '\n');
                break; 
                case 7: System.out.println("Program terminating ….");
            }
        } while (choice < 7);
    }
    /* add method code here */
}