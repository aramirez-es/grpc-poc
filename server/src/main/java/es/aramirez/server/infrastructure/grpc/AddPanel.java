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
    Mono.just(request.getName())
        .map(AddPanelUseCase.Request::new)
        .flatMapMany(this.addPanelService::execute)
        .map(useCaseResponse -> PanelResponse.newBuilder().setIndex(useCaseResponse.getNewPanelIndex()).build())
        .doOnNext(responseObserver::onNext)
        .doOnComplete(responseObserver::onCompleted)
        .subscribe();
  }
}
