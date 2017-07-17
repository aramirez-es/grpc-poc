package es.aramirez;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.function.Consumer;
import java.util.function.Function;

public class Client extends Application {

  private static final String NEW_PANEL_TEXT = "New panel";
  private static final String NEW_TASK_TEXT = "New task to do";

  private BorderPane root;

  public void init(Stage stage) throws Exception {
    BorderPane addNewPanel = new BorderPane();
    TextField addNewPanelName = new TextField(NEW_PANEL_TEXT);
    Button addNewPanelButton = new Button("Add");
    addNewPanel.setLeft(addNewPanelName);
    addNewPanel.setRight(addNewPanelButton);
    addNewPanel.setPadding(new Insets(30, 10, 0, 0));

    root = new BorderPane();
    root.setLeft(new EmptyPane());
    root.setRight(addNewPanel);

    stage.setTitle("TODO-List");
    stage.setScene(new Scene(root, 1024, 768));

    stage.show();
  }

  private CreatedPanel createNewPanel(String panelId, String panelTitle) {
    ObservableList<String> messages = FXCollections.observableArrayList();
    TextField newTask = new TextField(NEW_TASK_TEXT);
    Button addButton = new Button("Add");

    Platform.runLater(() -> {
      ListView<String> messagesView = new ListView<>();
      messagesView.setItems(messages);

      BorderPane addTaskPanel = new BorderPane();
      addTaskPanel.setId("panel_" + panelId + "_add_task");
      addTaskPanel.setCenter(newTask);
      addTaskPanel.setRight(addButton);

      BorderPane panel = new BorderPane();
      panel.setId("panel_" + panelId + "_panel");
      panel.setPadding(new Insets(10, 10, 10, 10));
      panel.setMaxWidth(240);
      panel.setTop(new Text(panelTitle));
      panel.setCenter(messagesView);
      panel.setBottom(addTaskPanel);

      updateView(panel);
    });

    return new CreatedPanel(panelId, messages, newTask, addButton);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    init(primaryStage);
    ClientChannel.init(createNewPanelWrapper());
  }

  @Override
  public void stop() throws Exception {
    ClientChannel.close();
  }

  private void updateView(BorderPane newPanel) {
    root.setLeft(((DynamicPanel) root.getLeft()).addPane(newPanel));
  }

  public static void main(String[] args) throws InterruptedException {
    launch(args);
  }

  private Function<PanelDetails, CreatedPanel> createNewPanelWrapper() {
    return (PanelDetails panelDetails) -> createNewPanel(panelDetails.getPanelId(), panelDetails.getPanelTitle());
  }

  public static class PanelDetails {
    private String panelId;
    private String panelTitle;

    PanelDetails(String panelId, String panelTitle) {
      this.panelId = panelId;
      this.panelTitle = panelTitle;
    }

    public String getPanelId() {
      return panelId;
    }

    public String getPanelTitle() {
      return panelTitle;
    }
  }

  class CreatedPanel {

    private String panelId;
    private ObservableList<String> tasks;
    private final TextField newTask;
    private final Button addButton;

    CreatedPanel(String panelId, ObservableList<String> tasks, TextField newTask, Button addButton) {
      this.panelId = panelId;
      this.tasks = tasks;
      this.newTask = newTask;
      this.addButton = addButton;
    }

    void addTask(String task) {
      Platform.runLater(() -> tasks.add(task));
    }

    public void onAddTaskButtonClick(Consumer<String> stringConsumer) {
      addButton.setOnAction(event -> Platform.runLater(() -> {
        stringConsumer.accept(newTask.getText());
        newTask.setText(NEW_TASK_TEXT);
      }));
    }

    public String getPanelId() {
      return panelId;
    }
  }
}
