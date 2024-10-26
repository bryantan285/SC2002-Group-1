import java.util.Scanner;

public class Shape2DApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the total number of shapes:");
        int totalShapes = scanner.nextInt();

        double totalArea = 0; // To store the total area of all shapes

        for (int i = 0; i < totalShapes; i++) {
            System.out.println("Choose the shape (1 for Circle, 2 for Triangle, 3 for Rectangle):");
            int choice = scanner.nextInt();

            Shape shape = null;

            switch (choice) {
                case 1:
                    System.out.println("Enter the radius of the circle:");
                    double radius = scanner.nextDouble();
                    shape = new Circle(radius);
                    break;

                case 2:
                    System.out.println("Enter the base and height of the triangle:");
                    double base = scanner.nextDouble();
                    double height = scanner.nextDouble();
                    shape = new Triangle(base, height);
                    break;

                case 3:
                    System.out.println("Enter the length and breadth of the rectangle:");
                    double length = scanner.nextDouble();
                    double breadth = scanner.nextDouble();
                    shape = new Rectangle(length, breadth);
                    break;

                default:
                    System.out.println("Invalid choice!");
                    i--; // Decrement i to ask again
                    continue;
            }

            // Add the area of the shape to the total
            totalArea += shape.calculateArea();
        }

        // Output the total area
        System.out.println("The total area of all shapes is: " + totalArea);
        scanner.close();
    }
}
