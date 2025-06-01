package adamyan.Obstacles;

import adamyan.Vector2D;

import java.util.Optional;

public class LineObstacle extends Obstacle {

    public final Vector2D startPos;
    public final Vector2D endPos;
    public final Vector2D deltaVector;

    public LineObstacle(Vector2D startPos, Vector2D endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.deltaVector = endPos.minus(startPos);
    }

    @Override
    public Optional<Vector2D> getIntersectionPoint() {
        // Todo implement
        return Optional.empty();
    }

    @Override
    public void drawOnCanvas() {
    }
}
