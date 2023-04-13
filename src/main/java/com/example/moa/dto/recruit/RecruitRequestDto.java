package com.example.moa.dto.recruit;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.Recruit;
import com.example.moa.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RecruitRequestDto {

    private String foodName;

    private List<Ingredient> ingredients;

    private int maxPeople;

    private String recruitDate;

    private String writerEmail;

    private String title;

    private String content;

    public Recruit toEntity(User writer) {

        LocalDateTime localDateTime = LocalDateTime.now();
        String createdAt = localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));

        return Recruit.builder()
                .foodName(foodName)
                .ingredients(ingredients)
                .maxPeople(maxPeople)
                .recruitDate(recruitDate)
                .writer(writer)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();
    }
}
