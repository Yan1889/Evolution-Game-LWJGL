package adamyan.Obstacles;

import adamyan.Vector2D;

import java.util.Optional;

/**
 * The super class of every obstacle
 */
public abstract class Obstacle {
    public abstract Optional<Vector2D> getIntersectionPoint(Vector2D origin, Vector2D rayVector);
    public abstract void drawOnCanvas();
}
