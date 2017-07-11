package es.aramirez.server.core.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PanelRepository {
  Mono<String> addPanel(Panel panel);
  Mono<Panel> getById(String id);
  Mono<Task> getTaskById(String id, String panelId);
  Mono<String> update(Panel panel);

  Flux<Panel> getPanels();
}
