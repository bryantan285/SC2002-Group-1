import java.util.Scanner;

public class Shape3DApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the total number of 3D shapes:");
        int totalShapes = scanner.nextInt();

        double totalSurfaceArea = 0; // To store the total surface area of all 3D shapes

        for (int i = 0; i < totalShapes; i++) {
            System.out.println("Choose the shape (1 for Sphere, 2 for Square-based Pyramid, 3 for Cuboid):");
            int choice = scanner.nextInt();

            Shape shape = null;

            switch (choice) {
                case 1: // Sphere
                    System.out.println("Enter the radius of the sphere:");
                    double radius = scanner.nextDouble();
                    shape = new Sphere(radius);
                    break;

                case 2: // Square-based Pyramid
                    System.out.println("Enter the base length and height of the square-based pyramid:");
                    double baseLength = scanner.nextDouble();
                    double height = scanner.nextDouble();
                    shape = new SquarePyramid(baseLength, height);
                    break;

                case 3: // Cuboid
                    System.out.println("Enter the length, width, and height of the cuboid:");
                    double length = scanner.nextDouble();
                    double width = scanner.nextDouble();
                    double heightCuboid = scanner.nextDouble();
                    shape = new Cuboid(length, width, heightCuboid);
                    break;

                default:
                    System.out.println("Invalid choice!");
                    i--; // Decrement i to retry
                    continue;
            }

            // Add the surface area of the shape to the total
            totalSurfaceArea += shape.calculateArea();
        }

        // Output the total surface area
        System.out.println("The total surface area of all 3D shapes is: " + totalSurfaceArea);
        scanner.close();
    }
}