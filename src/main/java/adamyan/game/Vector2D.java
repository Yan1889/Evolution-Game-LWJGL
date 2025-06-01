package adamyan.game;

/**
 * This represents a vector (like a position) in 2D space.
 * <p>
 * Note: adamyan.game.Vector2D objects are immutable (record classes) => when it is modified, a new object is created
 * @param x the x coordinate of the vector
 * @param y the y coordinate of the vector
 */
public record Vector2D(double x, double y) {


    /**
     * @param angle the angle of the vector
     * @return a vector of length 1
     */
    public static Vector2D unitVectorOfAngle(double angle) {
        return new Vector2D(
                Math.cos(angle),
                Math.sin(angle)
        );
    }

    /**
     * @return a vector of length 1 pointing in a random direction
     */
    public static Vector2D randomUnitVector() {
        return unitVectorOfAngle(Math.random() * Math.TAU);
    }

    /**
     * @param scalar
     * @return a new Vector
     */
    public Vector2D scale(double scalar) {
        return new Vector2D(
                x * scalar,
                y * scalar
        );
    }

    /**
     * @return the magnitude (=length) of the vector
     */
    public double magnitude() {
        return Math.hypot(x, y);
    }

    /**
     * @return the unit vector (= new vector with the same direction but a length of 1)
     */
    public Vector2D normalized() {
        return scale(1 / magnitude());
    }

    public Vector2D minus(Vector2D other) {
        return new Vector2D(
                x - other.x,
                y - other.y
        );
    }

    public Vector2D plus(Vector2D other) {
        return new Vector2D(
                x + other.x,
                y + other.y
        );
    }
}
