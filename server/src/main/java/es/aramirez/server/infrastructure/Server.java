package es.aramirez.server.infrastructure;

import es.aramirez.server.core.application.AddPanel;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Server {
  public static void main(String[] args) throws IOException, InterruptedException {

    final int port = 8000;
    io.grpc.Server server = ServerBuilder.forPort(port)
        .addService(new AddPanel())
        .build()
        .start();

    System.out.println("Server started, listening on " + port);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      // Use stderr here since the logger may have been reset by its JVM shutdown hook.
      System.err.println("*** shutting down gRPC server since JVM is shutting down");
      if (server != null) {
        server.shutdown();
        System.err.println("*** server shut down");
      }
    }));

    server.awaitTermination();
  }
}
