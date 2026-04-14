package com.example.producer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataScheduler {

    private final ApiService apiService;
    private final PulsarProducer pulsarProducer;

    public DataScheduler(ApiService apiService, PulsarProducer pulsarProducer) {
        this.apiService = apiService;
        this.pulsarProducer = pulsarProducer;
    }

    @Scheduled(fixedDelay = 30000) // Every 30 seconds
    public void fetchAndSend() {
        try {
            System.out.println("\n🔄 Starting data fetch...");
            User[] users = apiService.getUsers();
            
            for (User user : users) {
                pulsarProducer.send(user);
            }
            
            System.out.println("✅ Sent " + users.length + " users to Pulsar\n");
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
}
