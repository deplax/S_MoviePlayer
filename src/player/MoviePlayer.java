// 참조 사이트.
// http://download.eclipse.org/efxclipse/updates-released/1.2.0/site
// https://www.youtube.com/watch?v=bWl98dhvf8Q
// http://hajsoftutorial.com/javafx-tutorial/
// http://stackoverflow.com/questions/13030556/multiple-javafx-stages-in-fullscreen-mode

package player;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class MoviePlayer {

	private SimpleStringProperty fileName;
	private SimpleStringProperty status;
	private SimpleStringProperty currentTime;
	private SimpleStringProperty sound;

	MediaPlayer player = null;

	Stage subStage = new Stage();

	public MoviePlayer(File file, String fileName, Controller con) {

		subStage.setTitle("subStage");
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
		this.sound.set(player.isMute() ? "mute":"unmute");

		final VBox vbox = new VBox();

		root.getChildren().add(view);
		root.getChildren().add(vbox);

		final DoubleProperty width = view.fitWidthProperty();
		final DoubleProperty height = view.fitHeightProperty();
		width.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
		view.setPreserveRatio(false);

		Scene scene = new Scene(root, 400, 400, Color.BLACK);
		subStage.setScene(scene);
		subStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				player.stop();
				player.dispose();
				con.data.remove(player);
			}
		});
		subStage.show();

		// 일단 스크린을 받아온다.
		// ㄴ 스크린의 개수, 크기
		// 이건 윈도우쪽으로 가게되면 만들자.
		Screen screen = Screen.getPrimary();
		System.out.println(screen.getVisualBounds().getWidth());
		System.out.println(screen.getVisualBounds().getHeight());

		// 키 이벤트를 따로따로 걸어주자.
		EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					// if(subStage.isFullScreen())
					// {
					// //subStage.setFullScreen(false);
					// con.fullscreenout();
					// }
					// else
					// {
					// //subStage.setFullScreen(true);
					// con.fullscreen();
					// }
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

		player.currentTimeProperty().addListener(
				new ChangeListener<Duration>() {

					@Override
					public void changed(
							ObservableValue<? extends Duration> observable,
							Duration oldValue, Duration newValue) {
						updateValue();
						con.reflashTable();
					}
				});
	}

	public void updateValue() {
		this.sound.set(player.isMute() ? "mute":"unmute");
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
