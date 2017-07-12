package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.application.GetPanelUseCase;
import es.aramirez.todo.GetPanelRequest;
import es.aramirez.todo.GetPanelResponse;
import es.aramirez.todo.PanelResourceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.function.Function;
import java.util.stream.Collectors;

public class GetPanel extends PanelResourceGrpc.PanelResourceImplBase {
  private GetPanelUseCase getPanelUseCase;

  public GetPanel(GetPanelUseCase getPanelUseCase) {
    this.getPanelUseCase = getPanelUseCase;
  }

  @Override
  public StreamObserver<GetPanelRequest> getPanel(StreamObserver<GetPanelResponse> responseObserver) {
    return new StreamObserver<GetPanelRequest>() {
      @Override
      public void onNext(GetPanelRequest value) {
        getPanelUseCase.execute(new GetPanelUseCase.Request(value.getPanelId()))
            .map(panelResponseToGrpcMessage())
            .doOnNext(responseObserver::onNext)
            .subscribe()
        ;
      }

      @Override
      public void onError(Throwable t) {
        responseObserver.onError(t);
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }

  private Function<GetPanelUseCase.Response, GetPanelResponse> panelResponseToGrpcMessage() {
    return response -> GetPanelResponse.newBuilder()
        .setPanelId(response.getPanelId())
        .setTitle(response.getPanelName())
        .addAllTasks(response.getTasks().stream().map(taskResponseToGrpcMessage()).collect(Collectors.toList()))
        .build();
  }

  private Function<GetPanelUseCase.Response.Task, GetPanelResponse.Task> taskResponseToGrpcMessage() {
    return task -> GetPanelResponse.Task.newBuilder()
        .setTaskId(task.getTaskId())
        .setTitle(task.getTitle())
        .build();
  }
}
