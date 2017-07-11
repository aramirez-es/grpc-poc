package es.aramirez.server.infrastructure.grpc;

import es.aramirez.todo.PanelRequest;
import es.aramirez.todo.PanelResourceGrpc;
import es.aramirez.todo.PanelResponse;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class AddPanelTest {

  private static final String UNIQUE_SERVER_NAME = "in-process server for " + AddPanel.class;

  private final Server inProcessServer = InProcessServerBuilder
      .forName(UNIQUE_SERVER_NAME)
      .addService(new AddPanel())
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
  public void itShouldAddNewPanel() throws Exception {
    PanelResourceGrpc.PanelResourceBlockingStub blockingStub = PanelResourceGrpc.newBlockingStub(inProcessChannel);
    String newPanel = "New Panel";
    PanelResponse response = blockingStub.addPanel(PanelRequest.newBuilder().setName(newPanel).build());

    assertThat(response.getPanelId(), notNullValue());
  }
}