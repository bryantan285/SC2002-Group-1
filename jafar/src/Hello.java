import java.util.Scanner;
public class Hello
{
    public static class Minimum {
        public int findMin(int num1, int num2) {
            if (num1 < num2)
                return num1;
            else
                return num2;
        }

        public double findMin(double num1, double num2) {
            if (num1 < num2)
                return num1;
            else
                return num2;
        }

        public int findMin(int num1, int num2, int num3) {
            return findMin(findMin(num1, num2), findMin(num2, num3));
        }
    }
    public static void main(String[] args) {
        int x=10, y=20, z=5;
        double i=4.5, j=5.5;
        Minimum m = new Minimum();
        System.out.println("findMin(x,y) with int args = " + m.findMin(x,y));
        System.out.println("findMin(i,j) with double args =" + m.findMin(i,j));
        System.out.println("findMin(x,y,z) with int args = " + m.findMin(x,y,z));
    }
}
