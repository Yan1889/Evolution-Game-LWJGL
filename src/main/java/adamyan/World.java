package adamyan;

import adamyan.Obstacles.Obstacle;

import java.util.List;

/**
 * A world object represents the state of a game
 */
public class World {

    private List<AntPopulation> antPopulations;
    private List<Obstacle> obstacles;


    /**
     * Constructs a world
     * @param antPopulations with the given populations
     * @param obstacles with the given obstacles
     */
    public World(List<AntPopulation> antPopulations, List<Obstacle> obstacles) {
        this.antPopulations = antPopulations;
        this.obstacles = obstacles;
    }

    /**
     * This method gets called each frame
     */
    public void calculateFrame() {
        for (AntPopulation population : antPopulations) {
            population.calculateFrame();
        }
    }

    public void drawOnCanvas() {
    }

    public void startSimulation() {
    }
}
