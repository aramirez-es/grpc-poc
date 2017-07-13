package es.aramirez;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {
  private ObservableList<String> messages = FXCollections.observableArrayList();
  private Button addButton = new Button("Add Task");
  private TextField newTask = new TextField();
  private ListView<String> messagesView = new ListView<>();

  public void init(Stage stage) throws Exception {
    messagesView.setItems(messages);

    BorderPane pane = new BorderPane();
    pane.setCenter(newTask);
    pane.setRight(addButton);

    BorderPane root = new BorderPane();
    root.setCenter(messagesView);
    root.setBottom(pane);

    stage.setTitle("TODO-List");
    stage.setScene(new Scene(root, 480, 320));

    stage.show();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    init(primaryStage);
    ClientChannel.init(addButton, newTask, messages);
  }

  @Override
  public void stop() throws Exception {
    ClientChannel.close();
  }

  public static void main(String[] args) throws InterruptedException {
    launch(args);
  }
}
