package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService(new SpamCheckServiceImpl())
                .build()
                .start();

        System.out.println("Server started on port 50051");
        server.awaitTermination();
    }
}
