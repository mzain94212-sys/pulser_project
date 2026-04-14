package com.example.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class PulsarConsumer {

    @Value("${pulsar.url}")
    private String pulsarUrl;

    @Value("${pulsar.topic}")
    private String topic;

    @Value("${pulsar.subscription}")
    private String subscription;

    private final DbService dbService;
    private PulsarClient client;
    private Consumer<byte[]> consumer;
    private final ObjectMapper mapper = new ObjectMapper();

    public PulsarConsumer(DbService dbService) {
        this.dbService = dbService;
    }

    @PostConstruct
    public void init() throws Exception {
        client = PulsarClient.builder()
                .serviceUrl(pulsarUrl)
                .build();
        
        consumer = client.newConsumer()
                .topic(topic)
                .subscriptionName(subscription)
                .subscriptionType(SubscriptionType.Shared)
                .messageListener(this::onMessage)
                .subscribe();
        
        System.out.println("✅ Consumer listening on: " + topic);
    }

    private void onMessage(Consumer<byte[]> consumer, Message<byte[]> msg) {
        try {
            String json = new String(msg.getData());
            User user = mapper.readValue(json, User.class);
            
            dbService.saveUser(user);
            consumer.acknowledge(msg);
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            consumer.negativeAcknowledge(msg);
        }
    }

    @PreDestroy
    public void cleanup() throws Exception {
        if (consumer != null) consumer.close();
        if (client != null) client.close();
    }
}
