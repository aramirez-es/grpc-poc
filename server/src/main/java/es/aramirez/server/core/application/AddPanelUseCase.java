package es.aramirez.server.core.application;

import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class AddPanelUseCase {

  private List<String> panels = new ArrayList<>();

  public Mono<Response> execute(Request request) {

    return Mono.defer(() -> {
      if (!panels.contains(request.getName())) {
        panels.add(request.getName());
      }

      final int newPanelIndex = panels.indexOf(request.getName());

      return Mono.just(new Response(newPanelIndex));
    });
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
  public class Response {

    private int newPanelIndex;

    public Response(int newPanelIndex) {

      this.newPanelIndex = newPanelIndex;
    }

    public int getNewPanelIndex() {
      return newPanelIndex;
    }
  }
}
