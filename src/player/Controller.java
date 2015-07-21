package player;

import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.TableView;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Controller {
	ObservableList<MoviePlayer> data = null;
	private Duration syncTime = null;
	TableView<MoviePlayer> table = null;

	public Controller(ObservableList<MoviePlayer> data, TableView<MoviePlayer> table) {
		this.data = data;
		this.table = table;
	}

	public void allPlay() {
		for (MoviePlayer moviePlayer : data) {
			moviePlayer.player.play();
		}
	}

	public void allPause() {
		for (MoviePlayer moviePlayer : data) {
			moviePlayer.player.pause();
		}
	}
	
	public void allStop() {
		for (MoviePlayer moviePlayer : data) {
			moviePlayer.player.stop();
		}
	}
	
	public void fullscreen(){
		for (MoviePlayer moviePlayer : data) {
			moviePlayer.subStage.setFullScreen(true);
		}
	}
	
	public void fullscreenout(){
		for (MoviePlayer moviePlayer : data) {
			moviePlayer.subStage.setFullScreen(false);
		}
	}
	
	public Boolean isEveryplayerStop(){
		Boolean result = false;
		for (MoviePlayer moviePlayer : data) {
			System.out.println(moviePlayer.player.getStatus());
			if(moviePlayer.player.getStatus() != MediaPlayer.Status.PLAYING)
				result = true;
		}
		return result;
	}
	
	public void setSyncTime(Duration time){
		time = Duration.seconds((int)time.toSeconds());
		syncTime = time;
		
		
		System.out.println(time.toString());
		System.out.println(time.toSeconds());
		System.out.println((int)time.toSeconds());
	}
	
	public void sync(){
		for (MoviePlayer moviePlayer : data) {
			moviePlayer.player.seek(syncTime);
		}
		//allPlay();
	}
	
	public void reflashTable(){
		table.getColumns().get(0).setVisible(false);
		table.getColumns().get(0).setVisible(true);
	}
}
