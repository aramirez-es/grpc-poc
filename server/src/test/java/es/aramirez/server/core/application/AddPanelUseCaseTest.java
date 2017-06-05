package es.aramirez.server.core.application;

import org.junit.Test;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AddPanelUseCaseTest {
  @Test
  public void itShouldAddNewPanel() throws Exception {
    AddPanelUseCase service = new AddPanelUseCase();

    Mono<AddPanelUseCase.Response> response1 = service.execute(new AddPanelUseCase.Request("New Panel"));
    Mono<AddPanelUseCase.Response> response2 = service.execute(new AddPanelUseCase.Request("Another Panel"));

    assertThat(response1.block().getNewPanelIndex(), is(0));
    assertThat(response2.block().getNewPanelIndex(), is(1));
  }

  @Test
  public void itShouldNotAddPanelIfAlreadyExist() throws Exception {
    AddPanelUseCase service = new AddPanelUseCase();
    service.execute(new AddPanelUseCase.Request("New Panel"));
    Mono<AddPanelUseCase.Response> response2 = service.execute(new AddPanelUseCase.Request("New Panel"));

    assertThat(response2.block().getNewPanelIndex(), is(0));
  }
}