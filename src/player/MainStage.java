package player;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MainStage extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(javafx.stage.Stage primaryStage) throws Exception {

		primaryStage.setTitle("primaryStage");
		Group root = new Group();
		//FlowPane root = new FlowPane();
		
		Button btn = new Button("test");
		btn.setOnAction(eve-> new MoviePlayer());
		root.getChildren().add(btn);
		
		Scene scene = new Scene(root, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
