package com.example.moa.repository;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByUser(User user);
}
