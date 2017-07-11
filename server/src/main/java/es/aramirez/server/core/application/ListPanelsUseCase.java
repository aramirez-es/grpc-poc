package es.aramirez.server.core.application;

import es.aramirez.server.core.domain.PanelRepository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class ListPanelsUseCase {
  private PanelRepository repository;

  public ListPanelsUseCase(PanelRepository repository) {
    this.repository = repository;
  }

  public Mono<Response> execute(Request request) {
    return repository.getPanels()
        .map(panel -> new Response.Panel(panel.getId(), panel.getName()))
        .collect(Collectors.toList())
        .map(Response::new);
  }

  public static class Request {}

  public static class Response {
    private List<Panel> panels;

    public Response(List<Panel> panels) {
      this.panels = panels;
    }

    public List<Panel> getPanels() {
      return panels;
    }

    public static class Panel {
      private String id;
      private String name;

      public Panel(String id, String name) {
        this.id = id;
        this.name = name;
      }

      public String getId() {
        return id;
      }

      public String getName() {
        return name;
      }
    }
  }
}
