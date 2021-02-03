package core;

public class Vector2D {

    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2D copyOf(Vector2D vector2D) {
        return new Vector2D(vector2D.getX(), vector2D.getY());
    }

    public static Vector2D directionBetweenPositions(Position start, Position end) {
        Vector2D vector2D = new Vector2D(
                start.getX() - end.getX(),
                start.getY() - end.getY()
        );

        vector2D.normalize();
        return vector2D;
    }

    public static double dotProduct(Vector2D v1, Vector2D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        double length = length();

        x = x == 0 ? 0 : x/length;
        y = y == 0 ? 0 : y/length;
    }

    public void multiply(double speed) {
        x *= speed;
        y *= speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
