import java.util.Scanner;

public class CircleApp {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int option;
        Circle circle = null;

        System.out.println("==== Circle Computation =====");
        System.out.println("|1. Create a new circle     |");
        System.out.println("|2. Print Area              |");
        System.out.println("|3. Print circumference     |");
        System.out.println("|4. Quit                    |");
        System.out.println("=============================");
        System.out.println("Choose option (1-3):");

        option = sc.nextInt();
        while (option != 4){
            switch (option){
                case 1:
                    System.out.println("Enter the radius to compute the area and circumference");
                    double rad = sc.nextDouble();
                    circle = new Circle(rad);
                    System.out.println("A new circle is created");
                    break;

                case 2:
                    if (circle == null) System.out.println("Circle not initialised!");
                    else {
                        System.out.println("Area of circle");
                        circle.getRadius();
                        circle.printArea();
                    }
                    break;

                case 3:
                    if (circle == null) System.out.println("Circle not initialised!");
                    else {
                        System.out.println("Area of circle");
                        circle.getRadius();
                        circle.printCircumference();
                    }
                    break;

                default:
                    System.out.println("Bad choice");
            }
            System.out.println("Choose option (1-3):");
            option = sc.nextInt();
        }
        System.out.println("Thank you!!");
    }
}
