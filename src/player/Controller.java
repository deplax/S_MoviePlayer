package player;

import javafx.collections.ObservableList;

public class Controller {
	ObservableList<MoviePlayer> data = null;

	public Controller(ObservableList<MoviePlayer> data) {
		this.data = data;
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
}
