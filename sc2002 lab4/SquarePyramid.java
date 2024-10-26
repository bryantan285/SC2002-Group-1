public class SquarePyramid implements Shape {
    private double baseLength, height;

    public SquarePyramid(double baseLength, double height) {
        this.baseLength = baseLength;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        // Surface Area of Pyramid = Base Area + 2 * (BaseLength * SlantHeight) / 2
        // Slant height = sqrt(height^2 + (baseLength / 2)^2)
        double slantHeight = Math.sqrt((height * height) + (baseLength / 2) * (baseLength / 2));
        double baseArea = baseLength * baseLength;
        double lateralArea = baseLength * slantHeight;
        return baseArea + lateralArea;
    }
}
