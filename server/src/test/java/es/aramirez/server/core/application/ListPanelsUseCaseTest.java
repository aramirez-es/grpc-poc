package es.aramirez.server.core.application;

import es.aramirez.server.core.domain.Panel;
import es.aramirez.server.core.domain.PanelRepository;
import es.aramirez.server.infrastructure.repositories.InMemoryPanelRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import reactor.core.publisher.Flux;

import static org.hamcrest.MatcherAssert.assertThat;


public class ListPanelsUseCaseTest {
  @Test
  public void itShouldReturnTheListOfPanels() throws Exception {
    PanelRepository repository = new InMemoryPanelRepository();

    repository.addPanel(new Panel("Month")).block();
    repository.addPanel(new Panel("Week")).block();
    repository.addPanel(new Panel("Day")).block();

    ListPanelsUseCase useCase = new ListPanelsUseCase(repository);

    Flux<ListPanelsUseCase.Response> response = useCase.execute(new ListPanelsUseCase.Request());

    assertThat(response.collectList().block().size(), CoreMatchers.is(3));

  }
}
