package com.example.moa.repository;

import com.example.moa.domain.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
}
