

package player;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import javafx.util.Duration;
//import javafx.application.Application;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.event.EventHandler;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Slider;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.VBox;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.media.MediaView;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//
//// http://download.eclipse.org/efxclipse/updates-released/1.2.0/site
//// https://www.youtube.com/watch?v=bWl98dhvf8Q
//// http://hajsoftutorial.com/javafx-tutorial/
//public class MoviePlayer extends Application {
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		primaryStage.setTitle("Movie Player");
//		Path path = Paths.get("film/iu.mp4");
//
//		Group root = new Group();
//		Media media = new Media(path.toUri().toString());
//
//		final MediaPlayer player = new MediaPlayer(media);
//		MediaView view = new MediaView(player);
//
//		final VBox vbox = new VBox();
//		Slider slider = new Slider();
//		vbox.getChildren().add(slider);
//
//		root.getChildren().add(view);
//		root.getChildren().add(vbox);
//
//		Scene scene = new Scene(root, 400, 400, Color.BLACK);
//		primaryStage.setScene(scene);
//		primaryStage.show();
//
//		player.play();
//		player.setOnReady(new Runnable() {
//
//			@Override
//			public void run() {
//
//				int w = player.getMedia().getWidth();
//				int h = player.getMedia().getHeight();
//
//				System.out.println("w : " + w);
//				System.out.println("h : " + h);
//				primaryStage.setMinWidth(w);
//				primaryStage.setMinHeight(h);
//
//				vbox.setMinSize(w - 50, 100);
//				vbox.setTranslateX(25);
//				vbox.setTranslateY(h - 100);
//
//				slider.setMin(0.0);
//				slider.setValue(0.0);
//				slider.setMax(player.getTotalDuration().toSeconds());
//			}
//
//		});
//
//		player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
//			@Override
//			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue,
//					Duration newValue) {
//				slider.setValue(newValue.toMillis() / 1000);
//			}
//		});
//		
//
//		slider.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//
//				System.out.println("click");
//				//slider.get
//				System.out.println(slider.getValue());
//				player.seek(Duration.seconds(slider.getValue()));
//			}
//		});
//	}
//}

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MoviePlayer{
	public MoviePlayer() {
		
		Stage subStage = new Stage();
		subStage.setTitle("subStage");
		Group root = new Group();
		Scene Scene = new Scene(root, 200, 200);
		subStage.setScene(Scene);
		subStage.show();
	}

}
