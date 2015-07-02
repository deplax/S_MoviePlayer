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

public class MainStage extends Application {

	String fileName = null;
	File file = null;

	TableView<MoviePlayer> table = new TableView<>();
	ObservableList<MoviePlayer> data = FXCollections.observableArrayList();

	int no = 1;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(javafx.stage.Stage primaryStage) throws Exception {

		Scene scene = new Scene(new Group());
		primaryStage.setTitle("primaryStage");
		primaryStage.setWidth(400);
		primaryStage.setHeight(500);

		Label label = new Label("Image Controller");
		label.setFont(new Font("Arial", 20));
		table.setPrefSize(330, 400);

		// Table column Setting
		TableColumn columnFileName = new TableColumn("FileName");
		columnFileName
				.setCellValueFactory(new PropertyValueFactory<MoviePlayer, String>(
						"fileName"));
		TableColumn columnStatus = new TableColumn("Status");
		columnStatus
				.setCellValueFactory(new PropertyValueFactory<MoviePlayer, String>(
						"status"));
		TableColumn columnCurrentTime = new TableColumn("CurrentTime");
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
			}
		});

		Button addButton = new Button("add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (file != null) {
					data.add(new MoviePlayer(file, fileName));
					no++;
					file = null;
					filePath.setText("Select movie...");
				}
			}
		});

		Button delButon = new Button("del");
		delButon.setOnAction(new EventHandler<ActionEvent>() {

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

		HBox hboxTable = new HBox();
		hboxTable.setSpacing(5);
		hboxTable.getChildren().addAll(table, delButon);

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
	}
}

// 메인 창이 6개의 서브 창을 컨트롤 가능해야 함.
// 서브 창은 별도의 화면에서 전체화면이 가능해야 함.

// 동영상 파일을 2개 받자.
// 동영상 플레이어를 만든다.
// 전체 화면이 되도록 한다.

// 컨트롤러 최대화 안 되도록.