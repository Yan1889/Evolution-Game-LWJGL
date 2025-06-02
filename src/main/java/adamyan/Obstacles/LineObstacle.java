package adamyan.Obstacles;

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
    public Optional<Vector2D> getIntersectionPoint(Vector2D rayOrigin, Vector2D rayVector) {
        double x1 = startPos.x();
        double y1 = startPos.y();
        double x2 = startPos.plus(startPos).x();
        double y2 = startPos.plus(startPos).y();

        double x3 = rayOrigin.x();
        double y3 = rayOrigin.y();
        double x4 = rayOrigin.plus(rayVector).x();
        double y4 = rayOrigin.plus(rayVector).y();

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (denominator == 0) {
            // parallel
            return Optional.empty();
        }

        double pX = (x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4) / denominator;
        double pY = (x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4) / denominator;

        return Optional.of(new Vector2D(pX, pY));
    }

    @Override
    public void drawOnCanvas() {
    }
}
