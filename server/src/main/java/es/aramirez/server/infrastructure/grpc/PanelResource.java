package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.application.AddPanelUseCase;
import es.aramirez.server.core.application.AddTaskUseCase;
import es.aramirez.server.core.application.GetPanelUseCase;
import es.aramirez.server.core.application.ListPanelsUseCase;
import es.aramirez.todo.*;
import io.grpc.stub.StreamObserver;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

public class PanelResource extends PanelResourceGrpc.PanelResourceImplBase {

  private final ListPanelsUseCase listPanelsUseCase;
  private final AddPanelUseCase addPanelService;
  private final GetPanelUseCase getPanelUseCase;
  private final AddTaskUseCase addTaskUseCase;

  public PanelResource(
      ListPanelsUseCase listPanelsUseCase,
      AddPanelUseCase addPanelUseCase,
      GetPanelUseCase getPanelUseCase,
      AddTaskUseCase addTaskUseCase
  ) {
    this.listPanelsUseCase = listPanelsUseCase;
    this.addPanelService = addPanelUseCase;
    this.getPanelUseCase = getPanelUseCase;
    this.addTaskUseCase = addTaskUseCase;
  }

  @Override
  public void listPanels(ListPanelsRequest request, StreamObserver<ListPanelsResponse> responseObserver) {
    Mono.just(request)
        .map(listPanelsRequest -> new ListPanelsUseCase.Request())
        .flatMapMany(listPanelsUseCase::execute)
        .map(response -> ListPanelsResponse.newBuilder().setPanelId(response.getId()).setTitle(response.getName()).build())
        .doOnNext(responseObserver::onNext)
        .doOnComplete(responseObserver::onCompleted)
        .subscribe();
  }

  @Override
  public void addPanel(PanelRequest request, StreamObserver<PanelResponse> responseObserver) {
    Mono.just(request.getName())
        .map(AddPanelUseCase.Request::new)
        .flatMapMany(addPanelService::execute)
        .map(useCaseResponse -> PanelResponse.newBuilder().setPanelId(useCaseResponse.getPanelId()).build())
        .doOnNext(responseObserver::onNext)
        .doOnComplete(responseObserver::onCompleted)
        .subscribe();
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

  @Override
  public void addTask(TaskRequest request, StreamObserver<TaskResponse> responseObserver) {
    Mono.just(request)
        .map(taskRequest -> new AddTaskUseCase.AddTaskRequest(request.getPanelId(), request.getTitle()))
        .flatMapMany(addTaskUseCase::execute)
        .map(useCaseResponse -> TaskResponse.newBuilder().setTaskId(useCaseResponse.getId()).build())
        .doOnNext(responseObserver::onNext)
        .doOnComplete(responseObserver::onCompleted)
        .subscribe();
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
