package es.aramirez.server.core.application;

import es.aramirez.server.core.domain.Panel;
import es.aramirez.server.core.domain.PanelRepository;
import reactor.core.publisher.Mono;

public class AddPanelUseCase {

  private final PanelRepository panelRepository;

  public AddPanelUseCase(PanelRepository panelRepository) {
    this.panelRepository = panelRepository;
  }

  public Mono<Response> execute(Request request) {
    return Mono.just(new Panel(request.getName()))
        .flatMapMany(panelRepository::addPanel)
        .singleOrEmpty()
        .map(Response::new);
  }

  public static class Request {
    private String name;

    public Request(String name) {

      this.name = name;
    }

    public String getName() {
      return name;
    }

  }
  public static class Response {

    private String panelId;

    public Response(String panelId) {

      this.panelId = panelId;
    }

    public String getPanelId() {
      return panelId;
    }
  }
}
