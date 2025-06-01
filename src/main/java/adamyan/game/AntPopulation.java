package adamyan.game;

import adamyan.neuralengine.ActivationFunctions;
import adamyan.neuralengine.Layer;
import adamyan.neuralengine.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * represents a colony, made up of ants
 */
public class AntPopulation {
    private World world;
    private Vector2D nestPos;

    // ---- ant stuff ----
    private int antCount;
    private List<Ant> ants;
    private int rayCount;
    private double rayLength;
    private double maxVelocity;
    private double lifeSpan;

    // ---- genetic stuff ----
    private double elitePercentage;
    private Function<Ant, Double> fitnessFunction;



    public AntPopulation(Vector2D nestPos, int antCount, Function<Ant, Double> fitnessFunction, double elitePercentage) {
        this.nestPos = nestPos;
        this.antCount = antCount;
        this.fitnessFunction = fitnessFunction;
        this.elitePercentage = elitePercentage;

        Layer[] layers = {
                new Layer(4, ActivationFunctions::linear),
                new Layer(10, ActivationFunctions::sigmoid),
                new Layer(10, ActivationFunctions::sigmoid),
                new Layer(2, ActivationFunctions::sigmoid),
        };

        ants = new ArrayList<>(antCount);
        for (int i = 0; i < antCount; i++) {
            ants.add(new Ant(
                    nestPos,
                    new Network(layers)
            ));
        }
    }

    public void calculateFrame() {
        for (Ant ant : ants) {
            ant.act();
        }
    }
    public void flood() {}

    public void drawOnCanvas() {
        // ?
    }
}
