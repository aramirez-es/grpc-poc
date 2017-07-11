package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.application.ListPanelsUseCase;
import es.aramirez.todo.ListPanelsRequest;
import es.aramirez.todo.ListPanelsResponse;
import es.aramirez.todo.PanelResourceGrpc;
import io.grpc.stub.StreamObserver;
import reactor.core.publisher.Mono;

public class ListPanels extends PanelResourceGrpc.PanelResourceImplBase {
  private ListPanelsUseCase listPanelsUseCase;

  public ListPanels(ListPanelsUseCase listPanelsUseCase) {
    this.listPanelsUseCase = listPanelsUseCase;
  }

  @Override
  public void listPanels(ListPanelsRequest request, StreamObserver<ListPanelsResponse> responseObserver) {
    Mono.just(request)
        .map(listPanelsRequest -> new ListPanelsUseCase.Request())
        .flatMapMany(listPanelsUseCase::execute)
        .map(response -> ListPanelsResponse.getDefaultInstance())
        .doOnNext(responseObserver::onNext)
        .doOnComplete(responseObserver::onCompleted)
        .subscribe();
  }
}
