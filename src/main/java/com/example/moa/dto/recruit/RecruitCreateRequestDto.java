package com.example.moa.dto.recruit;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RecruitCreateRequestDto {

    private String foodName;

    private List<String> needIngredients;

    private int maxPeople;

    private String recruitDate;

    private String writerEmail;

    private String title;

    private String content;

    public Recruit toEntity(User writer) {

        return Recruit.builder()
                .foodName(foodName)
                .needIngredients(needIngredients)
                .maxPeople(maxPeople)
                .recruitDate(LocalDate.parse(recruitDate))
                .writer(writer)
                .title(title)
                .content(content)
                .createdAt(LocalDate.now())
                .build();
    }
}
