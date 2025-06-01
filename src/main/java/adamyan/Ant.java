package adamyan;

import adamyan.neuralengine.Network;

/**
 * represents a single ant
 */
public class Ant {
    private Network network;
    private Vector2D position;
    private double rotationAngle;

    //public static final Image antImage = new Image("ant.png");

    public Ant(String FileName) {

    }

    public Ant(Vector2D position, Network network) {
        this.position = position;
        this.network = network;
        this.rotationAngle = 0;
    }

    public void act() {
        // random movement
        position = position.plus(Vector2D.randomUnitVector());
    }

    private void move(double distance) {
        Vector2D movementVector = Vector2D
                .unitVectorOfAngle(rotationAngle)
                .scale(distance);
        position = position.plus(movementVector);
    }

    public void drawOnCanvas() {
    }

    public Vector2D getPosition() {
        return position;
    }
    public double getRotation() {
        return rotationAngle;
    }
}
