package adamyan.Obstacles;

import adamyan.Vector2D;
import javafx.scene.canvas.Canvas;

import java.util.List;
import java.util.Optional;

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
    public Optional<Vector2D> getIntersectionPoint() {
        // Todo implement
        return Optional.empty();
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        // Todo implement
    }




    public record VectorPair(Vector2D start, Vector2D end) {}
}
