// 참조 사이트.
// http://download.eclipse.org/efxclipse/updates-released/1.2.0/site
// https://www.youtube.com/watch?v=bWl98dhvf8Q
// http://hajsoftutorial.com/javafx-tutorial/
// http://stackoverflow.com/questions/13030556/multiple-javafx-stages-in-fullscreen-mode

package player;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class MoviePlayer {

	private SimpleStringProperty fileName;
	private SimpleStringProperty status;
	private SimpleStringProperty currentTime;
	private SimpleStringProperty sound;

	MediaPlayer player = null;
	Scene scene = null;
	Stage subStage = new Stage();

	Boolean stopFlag = false;

	double mouseX = 0;
	double mouseY = 0;
	double mousediffX = 0;
	double mousediffY = 0;
	
	Boolean mouseMoveFlag = true;
	
	long startTime = System.currentTimeMillis();
	long endTime = 0;

	public MoviePlayer(File file, String fileName, Controller con) {

		subStage.setTitle("©Jungju An");
		Group root = new Group();

		Path path = Paths.get(file.getAbsolutePath());
		Media media = new Media(path.toUri().toString());

		player = new MediaPlayer(media);
		MediaView view = new MediaView(player);

		this.fileName = new SimpleStringProperty(file.getName());
		this.status = new SimpleStringProperty(player.getStatus().toString());
		this.currentTime = new SimpleStringProperty(player.getCurrentTime()
				.toString());
		this.sound = new SimpleStringProperty();
		this.sound.set(player.isMute() ? "mute" : "unmute");

		final VBox vbox = new VBox();

		root.getChildren().add(view);
		root.getChildren().add(vbox);

		final DoubleProperty width = view.fitWidthProperty();
		final DoubleProperty height = view.fitHeightProperty();
		width.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
		view.setPreserveRatio(true);

		scene = new Scene(root, 400, 400, Color.BLACK);
		subStage.setScene(scene);
		subStage.centerOnScreen();
		subStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				player.stop();
				player.dispose();
				con.data.remove(player);
				updateValue();
				con.reflashTable();
			}
		});
		subStage.show();

		// 키 이벤트를 따로따로 걸어주자.
		EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				if (event.getCode() == KeyCode.ENTER) {
					System.out.println(player.getStatus());
					System.out.println(player.getCurrentTime());
				}
				if (event.getCode() == KeyCode.SPACE) {
					if (player.getStatus() != MediaPlayer.Status.PLAYING) {
						con.allPlay();

					} else {
						con.allPause();
						con.setSyncTime(player.getCurrentTime());
						con.sync();
						con.reflashTable();
					}
				}
				updateValue();
				con.reflashTable();

			}
		};
		scene.addEventHandler(KeyEvent.KEY_PRESSED, handler);

		player.setOnReady(new Runnable() {

			@Override
			public void run() {

				int w = player.getMedia().getWidth();
				int h = player.getMedia().getHeight();

				subStage.setMinWidth(w);
				subStage.setMinHeight(h);
				subStage.sizeToScene();

				vbox.setMinSize(w - 50, 100);
				vbox.setTranslateX(25);
				vbox.setTranslateY(h - 100);

				// windows7 frame size set
				subStage.setWidth(subStage.getWidth()
						+ (subStage.getWidth() - scene.getWidth()));
				subStage.setHeight(subStage.getHeight()
						+ (subStage.getHeight() - scene.getHeight()));
			}

		});

		player.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				stopFlag = true;
				if (con.isEveryplayerStop()) {
					con.stopFlagInit();
					System.out.println("isEveryplayerStop");
					con.setSyncTime(new Duration(0));
					con.sync();
					con.allPause();
					con.sync();
					con.allPlay();
					con.reflashTable();
				}
			}
		});

		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mouseX = event.getX();
				mouseY = event.getY();
				scene.setCursor(Cursor.DEFAULT);
			}
		});
		

		player.currentTimeProperty().addListener(
				new ChangeListener<Duration>() {
					
					@Override
					public void changed(
							ObservableValue<? extends Duration> observable,
							Duration oldValue, Duration newValue) {

						System.out.println(endTime - startTime);

						// 마우스가 움직이지 않았으면.
						if (mouseX == mousediffX && mouseY == mousediffY)
							mouseMoveFlag = false;
						else
							mouseMoveFlag = true;
						
						if(con.isMouseMove()){
							startTime = System.currentTimeMillis();
						}else{
							endTime = System.currentTimeMillis();
							if (endTime - startTime > 5000)
								scene.setCursor(Cursor.NONE);
						}

						mousediffX = mouseX;
						mousediffY = mouseY;
						
						updateValue();
						con.reflashTable();
					}
				});
	}

	public void updateValue() {
		this.sound.set(player.isMute() ? "mute" : "unmute");
		this.currentTime.set(player.getCurrentTime().toString());
		this.status.set(player.getStatus().toString());
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

	public String getSound() {
		return sound.get();
	}
}
