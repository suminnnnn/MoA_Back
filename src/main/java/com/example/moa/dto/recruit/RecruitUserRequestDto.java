package com.example.moa.dto.recruit;

import com.example.moa.dto.ingredient.IngredientRequestDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RecruitUserRequestDto {

    private List<Long> ingredient_id;

    private Long recruitId = null;

    private String userEmail=null;
}
