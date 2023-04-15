package com.example.moa.dto.ingredient;

import com.example.moa.domain.Ingredient;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class IngredientResponseDto {

    private Long id;
    private String name;
    private String registerDate;
    private String ingredientImage;
    private String receiptImage;
    private String purchasedDate;
    private String expirationDate;

    public static IngredientResponseDto from(Ingredient ingredient){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return IngredientResponseDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .registerDate(ingredient.getRegisteredDate().format(formatter))
                .ingredientImage(ingredient.getIngredientImage())
                .receiptImage(ingredient.getReceiptImage())
                .purchasedDate(ingredient.getPurchasedDate().format(formatter))
                .expirationDate(ingredient.getExpirationDate().format(formatter))
                .build();
    }
}