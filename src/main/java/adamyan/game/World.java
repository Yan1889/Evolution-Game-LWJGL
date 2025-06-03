package adamyan.game;

import adamyan.game.obstacles.LineObstacle;
import adamyan.game.obstacles.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A world object represents the state of a game
 */
public class World {

    private List<AntPopulation> antPopulations;
    private List<Obstacle> obstacles;


    /**
     * Constructor of a random world, used for testing
     */
    public World() {
        antPopulations = new ArrayList<>();
        antPopulations.add(new AntPopulation(
                new Vector2D(100, 100),
                10,
                ant -> ant.getPosition().x(),
                0.10,
                "src/main/resources/ant.png"
        ));

        obstacles = new ArrayList<>();
        obstacles.add(new LineObstacle(
                new Vector2D(50, 50),
                new Vector2D(100, 200)
        ));
    }


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
        // ?
        for (Obstacle o : obstacles) {
            o.drawOnCanvas();
        }
        for (AntPopulation p : antPopulations) {
            p.drawOnCanvas();
        }
    }

    public void startSimulation() {
        // how do I use the graphics?

        TimerTask frameTask = new TimerTask() {
            @Override
            public void run() {
                calculateFrame();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(frameTask, 0, 1000);
    }
}
