package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.application.ListPanelsUseCase;
import es.aramirez.server.core.domain.Panel;
import es.aramirez.server.infrastructure.repositories.InMemoryPanelRepository;
import es.aramirez.todo.*;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.StreamRecorder;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ListPanelsTest {

  private static final String UNIQUE_SERVER_NAME = "in-process server for " + ListPanels.class;

  private final InMemoryPanelRepository panelRepository = new InMemoryPanelRepository();

  private final Server inProcessServer = InProcessServerBuilder
      .forName(UNIQUE_SERVER_NAME)
      .addService(new ListPanels(new ListPanelsUseCase(panelRepository)))
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

    panelRepository.addPanel(new Panel("Month")).block();
    panelRepository.addPanel(new Panel("Week")).block();
    panelRepository.addPanel(new Panel("Day")).block();

    PanelResourceGrpc.PanelResourceStub blockingStub = PanelResourceGrpc.newStub(inProcessChannel);

    StreamRecorder<ListPanelsResponse> stream = StreamRecorder.create();
    blockingStub.listPanels(ListPanelsRequest.getDefaultInstance(), stream);

    assertThat(stream.awaitCompletion(5000, TimeUnit.MILLISECONDS), is(true));
    assertThat(stream.getError(), CoreMatchers.nullValue());
    assertThat(stream.getValues().size(), is(3));
  }
}
