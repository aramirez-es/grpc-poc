package es.aramirez.server.core.application;

import es.aramirez.server.infrastructure.repositories.InMemoryPanelRepository;
import reactor.core.publisher.Mono;

public class AddTaskUseCase {
  private InMemoryPanelRepository panelRepository;

  public AddTaskUseCase(InMemoryPanelRepository panelRepository) {
    this.panelRepository = panelRepository;
  }

  public Mono<AddTaskResponse> execute(AddTaskRequest addTaskRequest) {
    return panelRepository.getById(addTaskRequest.getPanelId())
        .doOnNext(panel -> panel.addTask(addTaskRequest.getTitle()))
        .flatMapMany(panel -> panelRepository.update(panel))
        .singleOrEmpty()
        .map(panel -> new AddTaskResponse(addTaskRequest.getTitle()));
  }

  public static class AddTaskRequest {
    private String panelId;
    private String title;

    public AddTaskRequest(String panelId, String title) {
      this.panelId = panelId;
      this.title = title;
    }

    public String getTitle() {
      return title;
    }

    public String getPanelId() {
      return panelId;
    }
  }

  public class AddTaskResponse {
    private String id;

    public AddTaskResponse(String id) {
      this.id = id;
    }

    public String getId() {
      return id;
    }
  }
}
