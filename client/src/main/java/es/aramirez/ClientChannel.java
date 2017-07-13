package es.aramirez;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class ClientChannel {

  public static final String PANEL = "";

  public static final String SERVER = "localhost";
  public static final int PORT = 8000;

  private static PanelResourceGrpc.PanelResourceStub streamingApi;
  private static PanelResourceGrpc.PanelResourceBlockingStub blockingApi;


  public static void init(Button addButton) {
    initializeClientChannel();

    addButton.setOnAction(event -> {
      Platform.runLater(() -> {
        System.out.println("Click!");
        TaskResponse response = blockingApi.addTask(TaskRequest.newBuilder().setPanelId(PANEL).setTitle("sasas").build());
        System.out.println(response.getTaskId());
      });
    });
  }

  private static void initializeClientChannel() {
    ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER, PORT).usePlaintext(true).build();

    streamingApi = PanelResourceGrpc.newStub(channel);
    blockingApi = PanelResourceGrpc.newBlockingStub(channel);

  }
}
