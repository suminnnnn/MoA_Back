package com.example.moa.repository;

import com.example.moa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    //User save(User user);
    Optional<User> findByEmail(String email);
}
