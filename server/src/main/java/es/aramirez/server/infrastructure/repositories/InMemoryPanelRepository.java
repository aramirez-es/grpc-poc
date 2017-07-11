package es.aramirez.server.infrastructure.repositories;

import es.aramirez.server.core.domain.Panel;
import es.aramirez.server.core.domain.PanelRepository;
import es.aramirez.server.core.domain.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

public class InMemoryPanelRepository implements PanelRepository {
  private Set<Panel> panels = new HashSet<>();


  @Override
  public Mono<String> addPanel(Panel panel) {
    return Mono.just(panel)
        .doOnNext(panels::add)
        .map(Panel::getId);
  }

  @Override
  public Mono<Panel> getById(String id) {
    return Flux.fromStream(panels.stream())
        .filter(panel -> panel.getId().equals(id))
        .singleOrEmpty();
  }

  @Override
  public Mono<Task> getTaskById(String id, String panelId) {
    return getById(panelId)
        .flatMapMany(panel -> Flux.fromIterable(panel.getTasks()))
        .filter(task -> task.getTaskId().equals(id))
        .singleOrEmpty();
  }

  @Override
  public Mono<String> update(Panel panel) {
    return Mono.just(panel.getId());
  }
}
