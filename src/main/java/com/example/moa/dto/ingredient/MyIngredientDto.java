package com.example.moa.dto.ingredient;

import lombok.*;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MyIngredientDto {
    private String name;
    private String purchasedDate;
    private String expirationDate;
}
