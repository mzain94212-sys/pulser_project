package com.example.producer;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {
    
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API = "https://jsonplaceholder.typicode.com/users";

    public User[] getUsers() {
        System.out.println("Fetching users from API...");
        return restTemplate.getForObject(API, User[].class);
    }
}
