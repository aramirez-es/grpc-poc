package es.aramirez.server.core.application;

import es.aramirez.server.core.domain.Panel;
import es.aramirez.server.core.domain.PanelRepository;
import es.aramirez.server.core.domain.Task;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetPanelUseCase {
  private PanelRepository repository;

  public GetPanelUseCase(PanelRepository repository) {
    this.repository = repository;
  }

  public Mono<Response> execute(Request request) {
    return repository.getById(request.getPanelId()).map(panelToDto());
  }

  private Function<Panel, Response> panelToDto() {
    return panel -> new Response(
        panel.getId(),
        panel.getName(),
        panel.getTasks()
            .stream()
            .map(taskToDto())
            .collect(Collectors.toList())
    );
  }

  private Function<Task, Response.Task> taskToDto() {
    return task -> new Response.Task(task.getTaskId(), task.getTitle());
  }

  public static class Request {
    private String panelId;

    public Request(String panelId) {
      this.panelId = panelId;
    }

    public String getPanelId() {
      return panelId;
    }
  }

  public static class Response {
    private String panelId;
    private String panelName;
    private List<Task> tasks;

    public Response(String panelId, String panelName, List<Task> tasks) {
      this.panelId = panelId;
      this.tasks = tasks;
      this.panelName = panelName;
    }

    public String getPanelId() {
      return panelId;
    }

    public String getPanelName() {
      return panelName;
    }

    public List<Task> getTasks() {
      return tasks;
    }

    public static class Task {
      private String taskId;
      private String title;

      public Task(String taskId, String title) {
        this.taskId = taskId;
        this.title = title;
      }

      public String getTaskId() {
        return taskId;
      }

      public String getTitle() {
        return title;
      }
    }
  }
}
