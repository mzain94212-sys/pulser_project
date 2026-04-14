package com.example.consumer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DbService {

    private final UserRepo repo;

    public DbService(UserRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public void saveUser(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User and user.id must not be null");
        }

        var existing = repo.findByUserId(user.getId());

        if (existing.isPresent()) {
            UserEntity entity = existing.get();
            copyToEntity(user, entity);
            repo.save(entity);
            System.out.println("🔄 Updated: " + user.getName());
        } else {
            UserEntity entity = new UserEntity();
            entity.setUserId(user.getId());
            copyToEntity(user, entity);
            repo.save(entity);
            System.out.println("💾 Saved: " + user.getName());
        }
    }

    private void copyToEntity(User user, UserEntity entity) {
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setUsername(user.getUsername());
        entity.setPhone(user.getPhone());
        entity.setWebsite(user.getWebsite());
    }
}
