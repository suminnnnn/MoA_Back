package com.example.moa.dto.ingredient;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.User;
import lombok.*;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class IngredientRequestDto {

    private Optional<String> userEmail;
    private String name;
    private String purchasedDate;
    private String expirationDate;
    private String ingredientImage;
    private String receiptImage;

    public Ingredient toEntity(User user){
        return Ingredient.builder()
                .name(name)
                .user(user)
                .ingredientImage(ingredientImage)
                .receiptImage(receiptImage)
                .purchasedDate(LocalDate.parse(purchasedDate))
                .expirationDate(LocalDate.parse(expirationDate))
                .registeredDate(LocalDate.now())
                .build();
    }
}