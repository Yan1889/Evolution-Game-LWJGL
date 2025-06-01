package adamyan;

import adamyan.Obstacles.LineObstacle;
import adamyan.Obstacles.Obstacle;
import adamyan.neuralengine.ActivationFunctions;
import adamyan.neuralengine.Layer;
import adamyan.neuralengine.Network;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    private Canvas canvas;

    /**
     * Here is where the javafx app starts
     */
    @Override
    public void start(Stage stage) {
        testNN();

        constructWindows();

        List<AntPopulation> testPop = new ArrayList<>();
        testPop.add(new AntPopulation(
                new Vector2D(400, 400),
                1000,
                ant -> ant.getPosition().x(),
                0.10
        ));
        List<Obstacle> testObstacles = new ArrayList<>();
        testObstacles.add(new LineObstacle(
                new Vector2D(300, 300),
                new Vector2D(500, 700)
        ));
        World testWorld = new World(testPop, testObstacles);
        testWorld.startSimulation(canvas);
    }

    private void constructWindows() {
        canvas = new Canvas(1000, 800);

        Group rootNode = new Group(canvas);

        Scene scene = new Scene(rootNode, 1000, 900);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Main window");
        stage.show();
    }

    private void testNN() {
        Layer[] layers = {
                new Layer(4, ActivationFunctions::linear),
                new Layer(10, ActivationFunctions::sigmoid),
                new Layer(10, ActivationFunctions::sigmoid),
                new Layer(2, ActivationFunctions::sigmoid),
        };
        Network network = new Network(layers);

        double[] output = network.feedForward(new double[]{0.2, 0.4, 0.5, 0.1});

        System.out.println(Arrays.toString(output));

        network.writeToFile();
    }

    public static void main(String[] args) {
        launch();
    }
}