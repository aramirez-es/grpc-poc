package es.aramirez.server.infrastructure;

import es.aramirez.server.core.application.AddPanelUseCase;
import es.aramirez.server.core.application.AddTaskUseCase;
import es.aramirez.server.core.application.ListPanelsUseCase;
import es.aramirez.server.infrastructure.grpc.AddPanel;
import es.aramirez.server.infrastructure.grpc.AddTask;
import es.aramirez.server.infrastructure.grpc.ListPanels;
import es.aramirez.server.infrastructure.repositories.InMemoryPanelRepository;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Server {
  public static void main(String[] args) throws IOException, InterruptedException {

    InMemoryPanelRepository panelRepository = new InMemoryPanelRepository();
    AddPanel addPanelGrpcService = new AddPanel(new AddPanelUseCase(panelRepository));
    AddTask addTaskGrpcService = new AddTask(new AddTaskUseCase(panelRepository));
    ListPanels listPanelsGrpcService = new ListPanels(new ListPanelsUseCase(panelRepository));

    final int port = 8000;
    io.grpc.Server server = ServerBuilder.forPort(port)
        .addService(addPanelGrpcService)
        .addService(addTaskGrpcService)
        .addService(listPanelsGrpcService)
        .build()
        .start();

    System.out.println("Server started, listening on " + port);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.err.println("*** shutting down gRPC server since JVM is shutting down");
      if (server != null) {
        server.shutdown();
        System.err.println("*** server shut down");
      }
    }));

    server.awaitTermination();
  }
}
