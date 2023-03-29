package com.example.moa.repository;

import com.example.moa.domain.User;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class SqlUserRepository implements UserRepository{

    private final EntityManager em;

    public SqlUserRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = em.find(User.class, email);
        return Optional.ofNullable(user);
    }
}
