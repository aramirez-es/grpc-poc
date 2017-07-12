package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.application.GetPanelUseCase;
import es.aramirez.server.core.domain.Panel;
import es.aramirez.server.infrastructure.repositories.InMemoryPanelRepository;
import es.aramirez.todo.*;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.StreamRecorder;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetPanelTest {
  private static final String UNIQUE_SERVER_NAME = "in-process server for " + GetPanel.class;

  private final InMemoryPanelRepository panelRepository = new InMemoryPanelRepository();

  private final Server inProcessServer = InProcessServerBuilder
      .forName(UNIQUE_SERVER_NAME)
      .addService(new GetPanel(new GetPanelUseCase(panelRepository)))
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
  public void itShouldGetAPanelWithTask() throws Exception {

    Panel monthPanel = new Panel("Month");
    monthPanel.addTask("go to super");
    monthPanel.addTask("clean the house");
    Panel weekPanel = new Panel("Week");

    panelRepository.addPanel(monthPanel).block();
    panelRepository.addPanel(weekPanel).block();

    PanelResourceGrpc.PanelResourceStub blockingStub = PanelResourceGrpc.newStub(inProcessChannel);

    StreamRecorder<GetPanelResponse> stream = StreamRecorder.create();
    StreamObserver<GetPanelRequest> panel = blockingStub.getPanel(stream);

    panel.onNext(GetPanelRequest.newBuilder().setPanelId(monthPanel.getId()).build());
    panel.onNext(GetPanelRequest.newBuilder().setPanelId(weekPanel.getId()).build());
    panel.onCompleted();

    assertThat(stream.awaitCompletion(5000, TimeUnit.MILLISECONDS), is(true));
    assertThat(stream.getError(), CoreMatchers.nullValue());
    assertThat(stream.getValues().size(), is(2));
    assertThat(stream.getValues().get(0).getPanelId(), is(monthPanel.getId()));
    assertThat(stream.getValues().get(1).getPanelId(), is(weekPanel.getId()));
  }
}
