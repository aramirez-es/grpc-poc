package es.aramirez;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: todo.proto")
public final class PanelResourceGrpc {

  private PanelResourceGrpc() {}

  public static final String SERVICE_NAME = "todo.PanelResource";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<es.aramirez.PanelRequest,
      es.aramirez.PanelResponse> METHOD_ADD_PANEL =
      io.grpc.MethodDescriptor.<es.aramirez.PanelRequest, es.aramirez.PanelResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "todo.PanelResource", "AddPanel"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              es.aramirez.PanelRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              es.aramirez.PanelResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<es.aramirez.TaskRequest,
      es.aramirez.TaskResponse> METHOD_ADD_TASK =
      io.grpc.MethodDescriptor.<es.aramirez.TaskRequest, es.aramirez.TaskResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "todo.PanelResource", "AddTask"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              es.aramirez.TaskRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              es.aramirez.TaskResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<es.aramirez.ListPanelsRequest,
      es.aramirez.ListPanelsResponse> METHOD_LIST_PANELS =
      io.grpc.MethodDescriptor.<es.aramirez.ListPanelsRequest, es.aramirez.ListPanelsResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "todo.PanelResource", "ListPanels"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              es.aramirez.ListPanelsRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              es.aramirez.ListPanelsResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<es.aramirez.GetPanelRequest,
      es.aramirez.GetPanelResponse> METHOD_GET_PANEL =
      io.grpc.MethodDescriptor.<es.aramirez.GetPanelRequest, es.aramirez.GetPanelResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "todo.PanelResource", "GetPanel"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              es.aramirez.GetPanelRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              es.aramirez.GetPanelResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PanelResourceStub newStub(io.grpc.Channel channel) {
    return new PanelResourceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PanelResourceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PanelResourceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PanelResourceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PanelResourceFutureStub(channel);
  }

  /**
   */
  public static abstract class PanelResourceImplBase implements io.grpc.BindableService {

    /**
     */
    public void addPanel(es.aramirez.PanelRequest request,
        io.grpc.stub.StreamObserver<es.aramirez.PanelResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_PANEL, responseObserver);
    }

    /**
     */
    public void addTask(es.aramirez.TaskRequest request,
        io.grpc.stub.StreamObserver<es.aramirez.TaskResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_TASK, responseObserver);
    }

    /**
     */
    public void listPanels(es.aramirez.ListPanelsRequest request,
        io.grpc.stub.StreamObserver<es.aramirez.ListPanelsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LIST_PANELS, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<es.aramirez.GetPanelRequest> getPanel(
        io.grpc.stub.StreamObserver<es.aramirez.GetPanelResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_GET_PANEL, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_ADD_PANEL,
            asyncUnaryCall(
              new MethodHandlers<
                es.aramirez.PanelRequest,
                es.aramirez.PanelResponse>(
                  this, METHODID_ADD_PANEL)))
          .addMethod(
            METHOD_ADD_TASK,
            asyncUnaryCall(
              new MethodHandlers<
                es.aramirez.TaskRequest,
                es.aramirez.TaskResponse>(
                  this, METHODID_ADD_TASK)))
          .addMethod(
            METHOD_LIST_PANELS,
            asyncServerStreamingCall(
              new MethodHandlers<
                es.aramirez.ListPanelsRequest,
                es.aramirez.ListPanelsResponse>(
                  this, METHODID_LIST_PANELS)))
          .addMethod(
            METHOD_GET_PANEL,
            asyncBidiStreamingCall(
              new MethodHandlers<
                es.aramirez.GetPanelRequest,
                es.aramirez.GetPanelResponse>(
                  this, METHODID_GET_PANEL)))
          .build();
    }
  }

  /**
   */
  public static final class PanelResourceStub extends io.grpc.stub.AbstractStub<PanelResourceStub> {
    private PanelResourceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PanelResourceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PanelResourceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PanelResourceStub(channel, callOptions);
    }

    /**
     */
    public void addPanel(es.aramirez.PanelRequest request,
        io.grpc.stub.StreamObserver<es.aramirez.PanelResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_PANEL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addTask(es.aramirez.TaskRequest request,
        io.grpc.stub.StreamObserver<es.aramirez.TaskResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_TASK, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listPanels(es.aramirez.ListPanelsRequest request,
        io.grpc.stub.StreamObserver<es.aramirez.ListPanelsResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_LIST_PANELS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<es.aramirez.GetPanelRequest> getPanel(
        io.grpc.stub.StreamObserver<es.aramirez.GetPanelResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(METHOD_GET_PANEL, getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class PanelResourceBlockingStub extends io.grpc.stub.AbstractStub<PanelResourceBlockingStub> {
    private PanelResourceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PanelResourceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PanelResourceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PanelResourceBlockingStub(channel, callOptions);
    }

    /**
     */
    public es.aramirez.PanelResponse addPanel(es.aramirez.PanelRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_PANEL, getCallOptions(), request);
    }

    /**
     */
    public es.aramirez.TaskResponse addTask(es.aramirez.TaskRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_TASK, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<es.aramirez.ListPanelsResponse> listPanels(
        es.aramirez.ListPanelsRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_LIST_PANELS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PanelResourceFutureStub extends io.grpc.stub.AbstractStub<PanelResourceFutureStub> {
    private PanelResourceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PanelResourceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PanelResourceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PanelResourceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<es.aramirez.PanelResponse> addPanel(
        es.aramirez.PanelRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_PANEL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<es.aramirez.TaskResponse> addTask(
        es.aramirez.TaskRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_TASK, getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_PANEL = 0;
  private static final int METHODID_ADD_TASK = 1;
  private static final int METHODID_LIST_PANELS = 2;
  private static final int METHODID_GET_PANEL = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PanelResourceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PanelResourceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_PANEL:
          serviceImpl.addPanel((es.aramirez.PanelRequest) request,
              (io.grpc.stub.StreamObserver<es.aramirez.PanelResponse>) responseObserver);
          break;
        case METHODID_ADD_TASK:
          serviceImpl.addTask((es.aramirez.TaskRequest) request,
              (io.grpc.stub.StreamObserver<es.aramirez.TaskResponse>) responseObserver);
          break;
        case METHODID_LIST_PANELS:
          serviceImpl.listPanels((es.aramirez.ListPanelsRequest) request,
              (io.grpc.stub.StreamObserver<es.aramirez.ListPanelsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PANEL:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getPanel(
              (io.grpc.stub.StreamObserver<es.aramirez.GetPanelResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class PanelResourceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return es.aramirez.TodoList.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PanelResourceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PanelResourceDescriptorSupplier())
              .addMethod(METHOD_ADD_PANEL)
              .addMethod(METHOD_ADD_TASK)
              .addMethod(METHOD_LIST_PANELS)
              .addMethod(METHOD_GET_PANEL)
              .build();
        }
      }
    }
    return result;
  }
}
