package es.aramirez;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientChannel {

  public static final String PANEL = "ea4c16dd-b540-4ef3-965a-f8c4aa55f7e8";

  public static final String SERVER = "localhost";
  public static final int PORT = 9090;

  private static ManagedChannel channel;
  private static PanelResourceGrpc.PanelResourceStub asyncApi;

  public static void init(Button addButton, TextField newTask, ObservableList<String> messages) {
    initializeClientChannel();

    StreamObserver<GetPanelRequest> panelRequestStream = asyncApi.getPanel(new StreamObserver<GetPanelResponse>() {
      @Override
      public void onNext(GetPanelResponse value) {
        Platform.runLater(() -> value.getTasksList().stream()
            .map(GetPanelResponse.Task::getTitle)
            .forEach(taskTitle -> messages.add("Task: " + taskTitle))
        );
      }

      @Override
      public void onError(Throwable t) {
        t.printStackTrace();
      }

      @Override
      public void onCompleted() {
        System.out.println("Completed!");
      }
    });

    panelRequestStream.onNext(GetPanelRequest.newBuilder().setPanelId(PANEL).build());

    addButton.setOnAction(event -> {
      Platform.runLater(() -> {
        TaskRequest taskRequest = TaskRequest.newBuilder().setPanelId(PANEL).setTitle(newTask.getText()).build();
        asyncApi.addTask(taskRequest, new StreamObserver<TaskResponse>() {
          @Override
          public void onNext(TaskResponse value) {
            Platform.runLater(() -> messages.add("Task: " + value.getTaskId()));
          }

          @Override
          public void onError(Throwable t) {
            t.printStackTrace();
          }

          @Override
          public void onCompleted() {

          }
        });
      });
    });
  }

  private static void initializeClientChannel() {
    channel = ManagedChannelBuilder.forAddress(SERVER, PORT).usePlaintext(true).build();
    asyncApi = PanelResourceGrpc.newStub(channel);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.err.println("*** shutting down channel since JVM is shutting down");
      if (channel != null) {
        channel.shutdown();
        System.err.println("*** channel shut down");
      }
    }));
  }

  public static void close() throws InterruptedException {
  }
}
