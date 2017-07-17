package es.aramirez;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.function.Consumer;
import java.util.function.Function;

class ClientChannel {
  private static final String SERVER = "localhost";
  private static final int PORT = 9090;

  private static ManagedChannel channel;
  private static PanelResourceGrpc.PanelResourceStub asyncApi;

  static void init(Function<Client.PanelDetails, Client.CreatedPanel> panelCreator) {
    initializeClientChannel();
    listPanels(panelCreator);
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

  private static void listPanels(
      Function<Client.PanelDetails, Client.CreatedPanel> panelCreator
  ) {
    asyncApi.listPanels(ListPanelsRequest.getDefaultInstance(), new StreamObserver<ListPanelsResponse>() {
      @Override
      public void onNext(ListPanelsResponse value) {
          String panelId = value.getPanelId();
          String panelTitle = value.getTitle();
          Client.CreatedPanel createdPanel = panelCreator.apply(new Client.PanelDetails(panelId, panelTitle));
          createGetPanelStream(createdPanel).onNext(GetPanelRequest.newBuilder().setPanelId(panelId).build());
          createdPanel.onAddTaskButtonClick(addNewTaskButton(createdPanel));
      }

      @Override
      public void onError(Throwable t) {
        t.printStackTrace();
        System.exit(-1);
      }

      @Override
      public void onCompleted() {
        System.out.println("List Panels Completed!");
      }
    });
  }

  private static Consumer<String> addNewTaskButton(Client.CreatedPanel createdPanel) {
    return (String newTask) -> {
      TaskRequest taskRequest = TaskRequest.newBuilder().setPanelId(createdPanel.getPanelId()).setTitle(newTask).build();
      asyncApi.addTask(taskRequest, new StreamObserver<TaskResponse>() {
        @Override
        public void onNext(TaskResponse value) {
          createdPanel.addTask(value.getTaskId());
        }

        @Override
        public void onError(Throwable t) {
          t.printStackTrace();
          System.exit(-1);
        }

        @Override
        public void onCompleted() {
          System.out.println("Add new task completed!");
        }
      });
    };
  }

  private static StreamObserver<GetPanelRequest> createGetPanelStream(Client.CreatedPanel createdPanel) {
    return asyncApi.getPanel(new StreamObserver<GetPanelResponse>() {
      @Override
      public void onNext(GetPanelResponse value) {
        value.getTasksList().stream()
            .map(GetPanelResponse.Task::getTitle)
            .forEach(createdPanel::addTask);
      }

      @Override
      public void onError(Throwable t) {
        t.printStackTrace();
        System.exit(-1);
      }

      @Override
      public void onCompleted() {
        System.out.println("List Panel's Tasks Completed!");
      }
    });
  }

  public static void close() throws InterruptedException {
  }
}
