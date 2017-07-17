package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.application.AddPanelUseCase;
import es.aramirez.server.core.application.AddTaskUseCase;
import es.aramirez.server.core.application.GetPanelUseCase;
import es.aramirez.server.core.application.ListPanelsUseCase;
import es.aramirez.server.infrastructure.repositories.InMemoryPanelRepository;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractTestPanelResource {
  private static final String UNIQUE_SERVER_NAME = "in-process server for " + PanelResource.class;

  protected final InMemoryPanelRepository panelRepository = new InMemoryPanelRepository();
  protected final Server inProcessServer = InProcessServerBuilder
      .forName(UNIQUE_SERVER_NAME)
      .addService(new PanelResource(
          new ListPanelsUseCase(panelRepository),
          new AddPanelUseCase(panelRepository),
          new GetPanelUseCase(panelRepository),
          new AddTaskUseCase(panelRepository)
      ))
      .directExecutor()
      .build();

  protected final ManagedChannel inProcessChannel = InProcessChannelBuilder
      .forName(UNIQUE_SERVER_NAME)
      .directExecutor()
      .build();

  @Before
  public void setUp() throws Exception {
    inProcessServer.start();
  }

  @After
  public void tearDown() throws Exception {
    inProcessChannel.shutdownNow();
    inProcessServer.shutdownNow();
  }
}
