package io.github.singhalmradul.authorizationserver.services;

public interface AmpqService {

    String QUEUE_NAME = "regiser-user-queue";
    String EXCHANGE_NAME = "register-user-exchange";
    String ROUTING_KEY = "register-user-routing-key";

    void sendMessage(Object message);

}