package com.example.moa.dto;

import com.example.moa.domain.User;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class IngredientDto {
    private String name;
    private LocalDate purchasedDate;
    private LocalDate expirationDate;
    private String ingredientImage;
    private String receiptImage;
    private User user;
}
