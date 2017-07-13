package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.domain.Panel;
import es.aramirez.todo.GetPanelRequest;
import es.aramirez.todo.GetPanelResponse;
import es.aramirez.todo.PanelResourceGrpc;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.StreamRecorder;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetPanelTest extends AbstractTestPanelResource {
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
