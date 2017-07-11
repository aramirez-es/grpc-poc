package es.aramirez.server.core.application;

import es.aramirez.server.core.domain.PanelRepository;
import reactor.core.publisher.Flux;

public class ListPanelsUseCase {
  private PanelRepository repository;

  public ListPanelsUseCase(PanelRepository repository) {
    this.repository = repository;
  }

  public Flux<Response> execute(Request request) {
    return repository.getPanels()
        .map(panel -> new Response(panel.getId(), panel.getName()));
  }

  public static class Request {}

  public static class Response {
      private String id;
      private String name;

      public Response(String id, String name) {
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
