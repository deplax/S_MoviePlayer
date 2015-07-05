package player;

import java.io.File;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	@Override
	public void start(javafx.stage.Stage primaryStage) throws Exception {
		
		testInit();

		Scene scene = new Scene(new Group());
		
//		scene.widthProperty().addListener(new ChangeListener<Number>() {
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, Number oldValue,
//					Number newValue) {
//				System.out.println("Width : " + scene.getWidth());
//				System.out.println("Width : " + primaryStage.getWidth());
//				
//			}
//		});
		
		primaryStage.setTitle("primaryStage");
		primaryStage.setWidth(400);
		primaryStage.setHeight(500);
		
		con = new Controller(data);
		

		Label label = new Label("Image Controller");
		label.setFont(new Font("Arial", 20));
		table.setPrefSize(330, 400);

		// Table column Setting
		TableColumn<MoviePlayer, String> columnFileName = new TableColumn<MoviePlayer, String>("FileName");
		columnFileName
				.setCellValueFactory(new PropertyValueFactory<MoviePlayer, String>(
						"fileName"));
		TableColumn<MoviePlayer, String> columnStatus = new TableColumn<MoviePlayer, String>("Status");
		columnStatus
				.setCellValueFactory(new PropertyValueFactory<MoviePlayer, String>(
						"status"));
		TableColumn<MoviePlayer, String> columnCurrentTime = new TableColumn<MoviePlayer, String>("CurrentTime");
		columnCurrentTime
				.setCellValueFactory(new PropertyValueFactory<MoviePlayer, String>(
						"CurrentTime"));

		table.setItems(data);
		table.getColumns().addAll(columnCurrentTime, columnFileName,
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
					//file = null;
					filePath.setText("Select movie...");
				}
			}
		});

		Button delButton = new Button("del");
		delButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MoviePlayer m = table.getSelectionModel().getSelectedItem();
				if (m != null) {
					System.out.println(m.getFileName());
					m.subStage.close();
					data.remove(m);
				}
			}
		});
		
		Button playButton = new Button("play");
		playButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				con.allPlay();
			}
		});

		HBox hboxTable = new HBox();
		hboxTable.setSpacing(5);
		hboxTable.getChildren().addAll(table, delButton, playButton);

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
		
		//windows7 frame size set
		primaryStage.setWidth(primaryStage.getWidth() + (primaryStage.getWidth() - scene.getWidth()));
		primaryStage.setHeight(primaryStage.getHeight() + (primaryStage.getHeight() - scene.getHeight()));
		
	}
	
	public void testInit(){
		file = new File("C:\\Users\\Administrator\\Desktop\\[MV] IU(아이유) _ Friday(금요일에 만나요) (Feat. Jang Yi-jeong(장이정) of HISTORY(히스토리)).mp4");
	}

}

// 각 화면이 최대화 되도록.
// 플레이어 싱크 기능.
// 사운드 컨트롤.