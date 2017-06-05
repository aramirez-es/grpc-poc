package es.aramirez.server.core.application;

import es.aramirez.todo.PanelRequest;
import es.aramirez.todo.PanelResourceGrpc;
import es.aramirez.todo.PanelResponse;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class AddPanel extends PanelResourceGrpc.PanelResourceImplBase {

  private List<String> panels = new ArrayList<>();

  @Override
  public void addPanel(PanelRequest request, StreamObserver<PanelResponse> responseObserver) {

    if (!panels.contains(request.getName())) {
      panels.add(request.getName());
    }

    final int newPanelIndex = panels.indexOf(request.getName());

    responseObserver.onNext(PanelResponse.newBuilder().setIndex(newPanelIndex).build());
  }
}
