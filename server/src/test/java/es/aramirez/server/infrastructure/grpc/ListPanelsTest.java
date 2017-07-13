package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.domain.Panel;
import es.aramirez.todo.ListPanelsRequest;
import es.aramirez.todo.ListPanelsResponse;
import es.aramirez.todo.PanelResourceGrpc;
import io.grpc.testing.StreamRecorder;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ListPanelsTest extends AbstractTestPanelResource {
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
