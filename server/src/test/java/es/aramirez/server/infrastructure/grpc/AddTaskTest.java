package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.application.AddTaskUseCase;
import es.aramirez.server.core.domain.Panel;
import es.aramirez.server.infrastructure.repositories.InMemoryPanelRepository;
import es.aramirez.todo.*;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class AddTaskTest {

  private static final String UNIQUE_SERVER_NAME = "in-process server for " + AddTask.class;

  private final InMemoryPanelRepository panelRepository = new InMemoryPanelRepository();

  private final Server inProcessServer = InProcessServerBuilder
      .forName(UNIQUE_SERVER_NAME)
      .addService(new AddTask(new AddTaskUseCase(panelRepository)))
      .directExecutor()
      .build();

  private final ManagedChannel inProcessChannel = InProcessChannelBuilder
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

  @Test
  public void itShouldAddNewTask() throws Exception {
    Panel panel = new Panel("Any panel");
    panelRepository.addPanel(panel).block();

    PanelResourceGrpc.PanelResourceBlockingStub blockingStub = PanelResourceGrpc.newBlockingStub(inProcessChannel);

    String newTask = "Buy some cool stuff";
    TaskResponse response = blockingStub.addTask(
        TaskRequest
            .newBuilder()
            .setTitle(newTask)
            .setPanelId(panel.getId())
            .build()
    );

    assertThat(response.getTaskId(), notNullValue());
  }
}
