package player;

import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class MainStage extends Application {

	String fileName = null;
	File file = null;
	Controller con = null;

	TableView<MoviePlayer> table = new TableView<>();
	ObservableList<MoviePlayer> data = FXCollections.observableArrayList();

	int no = 1;

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(javafx.stage.Stage primaryStage) throws Exception {

		testInit();

		int buttonWidth = 48;
		
		Scene scene = new Scene(new Group());

		primaryStage.setTitle("©Jungju An");
		primaryStage.setWidth(400);
		primaryStage.setHeight(500);
		primaryStage.setResizable(false);

		con = new Controller(data, table);

		Label label = new Label("Sync Controller");
		label.setFont(new Font("Arial", 20));
		table.setPrefSize(330, 400);

		// Table column Setting
		TableColumn<MoviePlayer, String> sound = new TableColumn<MoviePlayer, String>(
				"Sound");
		sound.setCellValueFactory(new PropertyValueFactory<MoviePlayer, String>(
				"sound"));

		TableColumn<MoviePlayer, String> columnFileName = new TableColumn<MoviePlayer, String>(
				"FileName");
		columnFileName
				.setCellValueFactory(new PropertyValueFactory<MoviePlayer, String>(
						"fileName"));
		TableColumn<MoviePlayer, String> columnStatus = new TableColumn<MoviePlayer, String>(
				"Status");
		columnStatus
				.setCellValueFactory(new PropertyValueFactory<MoviePlayer, String>(
						"status"));
		TableColumn<MoviePlayer, String> columnCurrentTime = new TableColumn<MoviePlayer, String>(
				"CurrentTime");
		columnCurrentTime
				.setCellValueFactory(new PropertyValueFactory<MoviePlayer, String>(
						"CurrentTime"));

		table.setItems(data);
		table.setEditable(true);
		table.getColumns().addAll(sound, columnCurrentTime, columnFileName,
				columnStatus);

		TextField filePath = new TextField();
		filePath.setPrefWidth(300);
		filePath.setDisable(true);
		filePath.setText("Select movie...");

		Button findFileButton = new Button("...");
		findFileButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open File");
				file = fileChooser.showOpenDialog(primaryStage);
				filePath.setText(file.getAbsolutePath());
				fileName = file.getName();
				System.out.println(file.getAbsolutePath());
			}
		});

		Button addButton = new Button("add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (file != null) {
					data.add(new MoviePlayer(file, fileName, con));
					no++;
					// 테스트를 위하여 file을 초기화 시키지 않는다.
					// file = null;
					filePath.setText("Select movie...");
				}
			}
		});

		Button delButton = new Button("del");
		delButton.setPrefWidth(buttonWidth);
		delButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				MoviePlayer m = table.getSelectionModel().getSelectedItem();
				if (m != null) {
					m.player.stop();
					m.player.dispose();
					m.subStage.close();
					data.remove(m);
					con.reflashTable();
				}
			}
		});

		Button muteButton = new Button("mute");
		muteButton.setPrefWidth(buttonWidth);
		muteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MoviePlayer m = table.getSelectionModel().getSelectedItem();
				if (m != null) {
					if (m.player.isMute())
						m.player.setMute(false);
					else
						m.player.setMute(true);
					m.updateValue();
					con.reflashTable();
				}
			}
		});
		
		Button syncButton = new Button("sync");
		syncButton.setPrefWidth(buttonWidth);
		syncButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MoviePlayer m = table.getSelectionModel().getSelectedItem();
				MoviePlayer m2 = data.get(0);
				if (m != null) {
					double diff = m.player.getCurrentTime().toSeconds() - m2.player.getCurrentTime().toSeconds();
					System.out.println("------------------");
					System.out.println(m.player.getCurrentTime());
					System.out.println(m2.player.getCurrentTime());
					System.out.println(diff);
					System.out.println("------------------");
					if(diff < -0.10){
						System.out.println("sync +");
						Duration time = Duration.seconds(0.3);
						m.player.seek(m.player.getCurrentTime().add(time));
					}else if(diff > 0.10){
						System.out.println("sync -");
						Duration time = Duration.seconds(0.3);
						m.player.seek(m.player.getCurrentTime().subtract(time));
					}
					diff = m.player.getCurrentTime().toSeconds() - m2.player.getCurrentTime().toSeconds();
					System.out.println(diff);
					System.out.println("------------------");
				}				
			}
		});

		VBox vboxCon = new VBox();
		vboxCon.setSpacing(5);
		vboxCon.getChildren().addAll(delButton, muteButton, syncButton);

		HBox hboxTable = new HBox();
		hboxTable.setSpacing(5);
		hboxTable.getChildren().addAll(table, vboxCon);

		HBox hbox = new HBox();
		hbox.setSpacing(5);
		hbox.getChildren().addAll(filePath, findFileButton, addButton);

		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, hboxTable, hbox);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		primaryStage.setScene(scene);
		primaryStage.show();

		// windows7 frame size set
		primaryStage.setWidth(primaryStage.getWidth()
				+ (primaryStage.getWidth() - scene.getWidth()));
		primaryStage.setHeight(primaryStage.getHeight()
				+ (primaryStage.getHeight() - scene.getHeight()));

	}

	public void testInit() {
		// file = new
		// File("C:\\Users\\Administrator\\Desktop\\[MV] IU(아이유) _ Friday(금요일에 만나요) (Feat. Jang Yi-jeong(장이정) of HISTORY(히스토리)).mp4");
		file = new File(
				"/Users/Deplax/Desktop/Where Am I (30 Second Short Film).mp4");
	}
}
