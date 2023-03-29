package com.example.moa.repository;

import com.example.moa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{
    //User save(User user);
    Optional<User> findByEmail(String email);
}
