

package player;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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


public class MoviePlayer{

	private final SimpleStringProperty fileName;
	private final SimpleStringProperty status;
	private final SimpleStringProperty currentTime;
	
	MediaPlayer player = null;
	
	Stage subStage = new Stage();
	
	public MoviePlayer(File file, String fileName, Controller con) {
		
		this.fileName = new SimpleStringProperty(file.getName());
		this.status = new SimpleStringProperty("stop");
		this.currentTime = new SimpleStringProperty("currentTime");

		subStage.setTitle("subStage");
		Group root = new Group();	
		
		Path path = Paths.get(file.getAbsolutePath());
		Media media = new Media(path.toUri().toString());

		player = new MediaPlayer(media);
		MediaView view = new MediaView(player);

		final VBox vbox = new VBox();
		//Slider slider = new Slider();
		//vbox.getChildren().add(slider);

		root.getChildren().add(view);
		root.getChildren().add(vbox);
		
		final DoubleProperty width = view.fitWidthProperty();
	    final DoubleProperty height = view.fitHeightProperty();
	    width.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
	    height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
	    view.setPreserveRatio(false);
	    

		Scene scene = new Scene(root, 400, 400, Color.BLACK);
		subStage.setScene(scene);
		subStage.show();
		
		EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					if(subStage.isFullScreen())
					{
						//subStage.setFullScreen(false);
						con.fullscreenout();
					}
					else
					{
						//subStage.setFullScreen(true);
						con.fullscreen();
					}
					
				}
				if(event.getCode() == KeyCode.SPACE){
					System.out.println(player.getStatus());
					if(player.getStatus() != MediaPlayer.Status.PLAYING)
						con.allPlay();
					else
						con.allPause();
				}
				 
			}
		};
		scene.addEventHandler(KeyEvent.KEY_PRESSED, handler);
		
		player.setOnReady(new Runnable() {

			@Override
			public void run() {

				int w = player.getMedia().getWidth();
				int h = player.getMedia().getHeight();

				System.out.println("w : " + w);
				System.out.println("h : " + h);
				subStage.setMinWidth(w);
				subStage.setMinHeight(h);
				subStage.sizeToScene();
				
				subStage.setAlwaysOnTop(true);

				vbox.setMinSize(w - 50, 100);
				vbox.setTranslateX(25);
				vbox.setTranslateY(h - 100);
				
				//player.getCurrentTime();
				
				//windows7 frame size set
				subStage.setWidth(subStage.getWidth() + (subStage.getWidth() - scene.getWidth()));
				subStage.setHeight(subStage.getHeight() + (subStage.getHeight() - scene.getHeight()));

//				slider.setMin(0.0);
//				slider.setValue(0.0);
//				slider.setMax(player.getTotalDuration().toSeconds());
			}

		});
	}

	public String getFileName() {
		return fileName.get();
	}

	public String getStatus() {
		return status.get();
	}
	
	public String getCurrentTime() {
		return currentTime.get();
	}


}
