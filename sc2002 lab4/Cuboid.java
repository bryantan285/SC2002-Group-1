public class Cuboid implements Shape {
    private double length, width, height;

    public Cuboid(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        // Surface Area of a Cuboid = 2(lw + lh + wh)
        return 2 * (length * width + length * height + width * height);
    }
}
