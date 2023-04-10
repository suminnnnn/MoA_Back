package com.example.moa.service;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.User;
import com.example.moa.dto.IngredientDto;
import com.example.moa.dto.UserDto;
import com.example.moa.repository.IngredientRepository;
import com.example.moa.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UserRepository userRepository;

    public void register(IngredientDto ingredientDto, User user) {
        LocalDate now = LocalDate.now();

        Ingredient ingredient = Ingredient.builder()
                .name(ingredientDto.getName())
                .user(user)
                .registeredDate(now)
                .purchasedDate(ingredientDto.getPurchasedDate())
                .expirationDate(ingredientDto.getExpirationDate())
                .build();

        ingredientRepository.save(ingredient);
    }
}