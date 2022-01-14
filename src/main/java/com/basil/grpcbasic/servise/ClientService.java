package com.basil.grpcbasic.servise;

import com.basil.grpc.GreetingServiceGrpc;
import com.basil.grpc.GreetingServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    @EventListener(ApplicationStartedEvent.class)
    public void manageChannel() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext().build();

        GreetingServiceGrpc.GreetingServiceBlockingStub stub =
                GreetingServiceGrpc.newBlockingStub(channel);

        GreetingServiceOuterClass.HelloRequest request = GreetingServiceOuterClass.HelloRequest
                .newBuilder().setName("Basil").build();

        GreetingServiceOuterClass.HelloResponse response = stub.greeting(request);

        LOGGER.info(response.toString());
        //ЗАКРЫВАЙ КАНАЛ иначе : SocketException на сервере!!!
        channel.shutdown();
    }

}
