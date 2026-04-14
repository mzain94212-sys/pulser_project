package com.example.consumer;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;
    private String name;
    private String email;
    private String username;
    private String phone;
    private String website;
    
    private LocalDateTime createdAt;
    
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
