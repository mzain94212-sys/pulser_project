package com.example.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class PulsarProducer {

    @Value("${pulsar.url}")
    private String pulsarUrl;

    @Value("${pulsar.topic}")
    private String topic;

    private PulsarClient client;
    private Producer<byte[]> producer;
    private final ObjectMapper mapper = new ObjectMapper(); //convert java ibj into json format

    @PostConstruct
    public void init() throws Exception {
        client = PulsarClient.builder()
                .serviceUrl(pulsarUrl)
                .build();
        
        producer = client.newProducer()
                .topic(topic)
                .create();
        
        System.out.println("✅ Producer connected to Pulsar");
    }

    public void send(User user) throws Exception {
        String json = mapper.writeValueAsString(user);
        producer.send(json.getBytes());
        System.out.println("📤 Sent: " + user.getName());
    }

    @PreDestroy
    public void cleanup() throws Exception {
        if (producer != null) producer.close();
        if (client != null) client.close();
    }
}
