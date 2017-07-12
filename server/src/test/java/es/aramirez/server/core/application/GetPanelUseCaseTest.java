package es.aramirez.server.core.application;

import es.aramirez.server.core.domain.Panel;
import es.aramirez.server.core.domain.PanelRepository;
import es.aramirez.server.infrastructure.repositories.InMemoryPanelRepository;
import org.junit.Test;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetPanelUseCaseTest {
  @Test
  public void itShouldGetAnExistingPanelWithItsTask() throws Exception {
    PanelRepository repository = new InMemoryPanelRepository();

    Panel dayPanel = new Panel("Day");
    Panel weekPanel = new Panel("Week");
    Panel monthPanel = new Panel("Month");

    dayPanel.addTask("go to run");
    dayPanel.addTask("go work");
    weekPanel.addTask("go to buy something");

    repository.addPanel(dayPanel).block();
    repository.addPanel(weekPanel).block();
    repository.addPanel(monthPanel).block();

    GetPanelUseCase useCase = new GetPanelUseCase(repository);
    Mono<GetPanelUseCase.Response> responseStream = useCase.execute(new GetPanelUseCase.Request(weekPanel.getId()));

    GetPanelUseCase.Response response = responseStream.block();
    assertThat(response.getPanelName(), is(weekPanel.getName()));
    assertThat(response.getTasks().size(), is(1));
  }
}
