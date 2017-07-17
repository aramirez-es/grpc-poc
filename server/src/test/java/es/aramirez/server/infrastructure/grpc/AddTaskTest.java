package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.domain.Panel;
import es.aramirez.todo.PanelResourceGrpc;
import es.aramirez.todo.TaskRequest;
import es.aramirez.todo.TaskResponse;
import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class AddTaskTest extends AbstractTestPanelResource {
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
