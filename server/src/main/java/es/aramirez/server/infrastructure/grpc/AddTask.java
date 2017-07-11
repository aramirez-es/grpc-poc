package es.aramirez.server.infrastructure.grpc;

import es.aramirez.server.core.application.AddTaskUseCase;
import es.aramirez.todo.PanelResourceGrpc;
import es.aramirez.todo.TaskRequest;
import es.aramirez.todo.TaskResponse;
import io.grpc.stub.StreamObserver;
import reactor.core.publisher.Mono;

public class AddTask extends PanelResourceGrpc.PanelResourceImplBase {

  private final AddTaskUseCase addTaskUseCase;

  public AddTask(AddTaskUseCase addTaskUseCase) {
    this.addTaskUseCase = addTaskUseCase;
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
}
