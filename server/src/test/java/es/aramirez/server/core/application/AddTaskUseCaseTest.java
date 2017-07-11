package es.aramirez.server.core.application;

import es.aramirez.server.core.domain.Panel;
import es.aramirez.server.infrastructure.repositories.InMemoryPanelRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import reactor.core.publisher.Mono;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AddTaskUseCaseTest {
  @Test
  public void itShouldAddNewTask() throws Exception {

    Panel panel = new Panel("any panel name");
    InMemoryPanelRepository panelRepository = new InMemoryPanelRepository();
    panelRepository.addPanel(panel).block();
    AddTaskUseCase service = new AddTaskUseCase(panelRepository);

    String panelId = panel.getId();
    String taskTitle = "Say hello world";

    AddTaskUseCase.AddTaskRequest request = new AddTaskUseCase.AddTaskRequest(panelId, taskTitle);
    Mono<AddTaskUseCase.AddTaskResponse> response = service.execute(request);

    String taskId = response.block().getId();
    assertThat(panelRepository.getTaskById(taskId, panelId).block().getTitle(), CoreMatchers.is(taskTitle));
  }
}
