package es.aramirez.server.infrastructure;

import es.aramirez.server.core.application.AddPanelUseCase;
import es.aramirez.server.core.application.AddTaskUseCase;
import es.aramirez.server.core.application.GetPanelUseCase;
import es.aramirez.server.core.application.ListPanelsUseCase;
import es.aramirez.server.core.domain.Panel;
import es.aramirez.server.infrastructure.grpc.PanelResource;
import es.aramirez.server.infrastructure.repositories.InMemoryPanelRepository;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Server {
  public static void main(String[] args) throws IOException, InterruptedException {

    Panel panelMonthly = new Panel("Monthly");
    Panel panelWeekly = new Panel("Weekly");
    Panel panelDaily = new Panel("Daily");
    panelMonthly.addTask("Go to the super!");
    panelWeekly.addTask("Clean he house");
    panelWeekly.addTask("Go to the super");
    panelDaily.addTask("Walking the dog");
    panelDaily.addTask("Hit the gym");
    panelDaily.addTask("Go work");
    System.out.println("Panel ID: " + panelMonthly.getId());
    System.out.println("Panel ID: " + panelWeekly.getId());
    System.out.println("Panel ID: " + panelDaily.getId());

    InMemoryPanelRepository panelRepository = new InMemoryPanelRepository();
    panelRepository.addPanel(panelMonthly).block();
    panelRepository.addPanel(panelWeekly).block();
    panelRepository.addPanel(panelDaily).block();

    PanelResource panelResourceService = new PanelResource(
        new ListPanelsUseCase(panelRepository),
        new AddPanelUseCase(panelRepository),
        new GetPanelUseCase(panelRepository),
        new AddTaskUseCase(panelRepository)
    );

    final int port = 9090;
    io.grpc.Server server = ServerBuilder.forPort(port)
        .addService(panelResourceService)
        .build();

    System.out.println("Attached services :");
    server.getServices()
        .stream()
        .map(serverServiceDefinition1 -> serverServiceDefinition1.getServiceDescriptor().getName())
        .forEach(s -> System.out.println("\t" + s));

    System.out.println("");
    System.out.println("Services methods: ");
    server.getServices()
        .forEach(serverServiceDefinition -> {
          serverServiceDefinition.getMethods().forEach(serverMethodDefinition -> {
            System.out.println("\t" + serverMethodDefinition.getMethodDescriptor().getFullMethodName());
          });
        });

    server.start();
    System.out.println("");
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
