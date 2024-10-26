import java.lang.Math;
import java.util.Scanner;

public class Lab2p1_Methods{
    public static void mulTest() {
        Scanner sc = new Scanner(System.in);
        int answer, correct = 0;

        for (int i = 0; i < 5; i++) {
            int number1 = (int) Math.floor(Math.random()*9) + 1;
            int number2 = (int) Math.floor(Math.random()*9) + 1;
            System.out.printf("How much is %d times %d? ", number1, number2);
            answer = sc.nextInt();

            correct += (answer == number1 * number2) ? 1 : 0;
        }
        System.out.printf(correct + " answers out of 5 are correct\n");
    }

    public static int divide(int m, int n) {
        int quotient = 0;
        while (m - (quotient+1)*n >= 0) quotient++;
        return quotient;
    }

    public static int modulus(int m, int n) {
        while (m - n >= 0) m -= n;
        return m;

    }
    public static int countDigits(int n) {
        int count = 0;
        if (n<=0) {
            return -1;
        }
        while (n > 0) {
            count++;
            n /= 10;
        }
        return count;
    }

    public static int position(int n, int digit){
        String nString = Integer.toString(n);
        String toFind = Integer.toString(digit);
        return nString.lastIndexOf(toFind)+1;
    }
    public static long extractOddDigits(long n) {
        if (n<0) return 0;
        String nString = Long.toString(n);
        String oddString = "";
        for (int i = 0; i<nString.length(); i++){
            if (nString.charAt(i)%2==1) oddString += nString.charAt(i);
        }
        return (oddString=="")?-1:Long.parseLong(oddString);
    }
}
