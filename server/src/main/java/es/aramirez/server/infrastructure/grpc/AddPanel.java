package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.application.AddPanelUseCase;
import es.aramirez.todo.PanelRequest;
import es.aramirez.todo.PanelResourceGrpc;
import es.aramirez.todo.PanelResponse;
import io.grpc.stub.StreamObserver;
import reactor.core.publisher.Mono;

public class AddPanel extends PanelResourceGrpc.PanelResourceImplBase {

  private final AddPanelUseCase addPanelService;

  public AddPanel() {
    this.addPanelService = new AddPanelUseCase();
  }

  @Override
  public void addPanel(PanelRequest request, StreamObserver<PanelResponse> responseObserver) {

    AddPanelUseCase.Request addPanelRequest = new AddPanelUseCase.Request(request.getName());
    Mono<AddPanelUseCase.Response> addPanelResponse = this.addPanelService.execute(addPanelRequest);

    responseObserver.onNext(PanelResponse.newBuilder().setIndex(addPanelResponse.block().getNewPanelIndex()).build());
  }
}
