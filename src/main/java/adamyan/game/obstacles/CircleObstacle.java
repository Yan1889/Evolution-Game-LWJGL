package adamyan.game.obstacles;

import adamyan.game.Vector2D;

import java.util.*;

public class CircleObstacle extends Obstacle {

    public final Vector2D centerPos;
    public final List<VectorPair> wallStartAndEndPoints;

    /**
     * @param centerPos the center of the circle
     * @param wallStartAndEndPoints stores all the wall segments by their start and end points
     */
    public CircleObstacle(Vector2D centerPos, List<VectorPair> wallStartAndEndPoints) {
        this.centerPos = centerPos;
        this.wallStartAndEndPoints = wallStartAndEndPoints;
    }

    @Override
    public Optional<Vector2D> getIntersectionPoint(Vector2D rayOrigin, Vector2D rayVector) {
        // Todo implement
        return Optional.empty();
    }

    @Override
    public void drawOnCanvas() {
        // Todo implement
    }

    public record VectorPair(Vector2D start, Vector2D end) {}
}
