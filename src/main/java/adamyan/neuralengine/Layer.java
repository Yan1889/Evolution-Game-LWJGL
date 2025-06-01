package adamyan.neuralengine;

import java.util.function.Function;

/**
 * Represents a single layer in the neural network
 */
public record Layer(int length, Function<Double, Double> activationFunction) {
    @Override
    public String toString() {
        return length + "";
    }
}
