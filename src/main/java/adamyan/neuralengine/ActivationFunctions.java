package adamyan.neuralengine;

/**
 * Collection of activation functions for neural networks
 */
public final class ActivationFunctions {
    private ActivationFunctions() {
    }

    public static double linear(double x) {
        return x;
    }

    public static double sigmoid(double x) {
        return 1.0f / (1.0f + Math.exp(-x));
    }

    public static double tanh(double x) {
        return Math.tanh(x);
    }

    public static double relu(double x) {
        return x > 0.0f ? x : 0.0f;
    }

    public static double lrelu(double x, double alpha) {
        return x > 0.0f ? x : alpha * x;
    }

    public static double lrelu(double x) {
        return lrelu(x, 0.01f);
    }

    public static double elu(double x, double alpha) {
        return x >= 0.0f ? x : alpha * (Math.exp(x) - 1.0f);
    }

    public static double elu(double x) {
        return elu(x, 1.0f);
    }

    public static double gelu(double x) {
        double sqrt2OverPi = Math.sqrt(2 / Math.PI);
        return (0.5 * x * (1 + Math.tanh(sqrt2OverPi * (x + 0.044715 * Math.pow(x, 3)))));
    }
}