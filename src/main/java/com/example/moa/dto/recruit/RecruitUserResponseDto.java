package com.example.moa.dto.recruit;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.Recruit;
import com.example.moa.domain.RecruitUser;
import com.example.moa.dto.ingredient.IngredientResponseDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RecruitUserResponseDto {

    private Long id;

    private String name;

    private List<IngredientResponseDto> ingredientResponseDto;

    public static RecruitUserResponseDto from(RecruitUser recruitUser) {
        return RecruitUserResponseDto.builder()
                .id(recruitUser.getId())
                .name(recruitUser.getUser().getName())
                .ingredientResponseDto(
                        recruitUser.getIngredients()
                        .stream()
                        .map(IngredientResponseDto::from)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
