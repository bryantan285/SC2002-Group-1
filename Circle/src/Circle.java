public class Circle {
    private double radius; 
    private static final double PI = 3.14159;

    public Circle(double rad) {
        this.radius = rad;
    }

    public void setRadius(double rad){
        this.radius = rad;
    }

    public double getRadius(){
        System.out.println("Radius: " + radius);
        return 0;
    }

    public double area(){
        return PI * radius * radius;
    }
    // calculate circumference
    public double circumference() {
        return 2 * PI * radius;
    }
    // print area
    public void printArea(){
        System.out.println(this.area());
    }
    // print circumference
    public void printCircumference(){
        System.out.println(this.circumference());
    }
}