package com.example.moa.dto.ingredient;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class IngredientImageDto {
    private List<String> result;
    private String ingredientImage;
}
