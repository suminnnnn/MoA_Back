package com.example.moa.service;

import com.example.moa.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;



}
