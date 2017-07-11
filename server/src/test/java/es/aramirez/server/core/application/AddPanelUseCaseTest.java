package es.aramirez.server.core.application;

import es.aramirez.server.infrastructure.repositories.InMemoryPanelRepository;
import org.junit.Test;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AddPanelUseCaseTest {
  @Test
  public void itShouldAddNewPanel() throws Exception {
    InMemoryPanelRepository panelRepository = new InMemoryPanelRepository();
    AddPanelUseCase service = new AddPanelUseCase(panelRepository);

    Mono<AddPanelUseCase.Response> response = service.execute(new AddPanelUseCase.Request("New Panel"));

    assertThat(panelRepository.getById(response.block().getPanelId()).block().getName(), is("New Panel"));
  }
}