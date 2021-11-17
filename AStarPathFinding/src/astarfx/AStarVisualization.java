package astarfx;

import javafx.application.Application;
import javafx.stage.Stage;

public class AStarVisualization extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("A* Pathfinding");
		AStarVisualizationController controller = new AStarVisualizationController();
		controller.initVisualization();
		primaryStage.setScene(controller.getPathScene());
		primaryStage.show();
	}

}
