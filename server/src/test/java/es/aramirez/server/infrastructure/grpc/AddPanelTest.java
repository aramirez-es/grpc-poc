package es.aramirez.server.infrastructure.grpc;

import es.aramirez.todo.PanelRequest;
import es.aramirez.todo.PanelResourceGrpc;
import es.aramirez.todo.PanelResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class AddPanelTest extends AbstractTestPanelResource {

  @Test
  public void itShouldAddNewPanel() throws Exception {
    PanelResourceGrpc.PanelResourceBlockingStub blockingStub = PanelResourceGrpc.newBlockingStub(inProcessChannel);
    String newPanel = "New Panel";
    PanelResponse response = blockingStub.addPanel(PanelRequest.newBuilder().setName(newPanel).build());

    assertThat(response.getPanelId(), notNullValue());
    assertThat(panelRepository.getById(response.getPanelId()).block().getName(), is(newPanel));
  }
}