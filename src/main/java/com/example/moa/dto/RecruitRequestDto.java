package com.example.moa.dto;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.User;
import com.example.moa.service.UserService;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RecruitRequestDto {

    private String foodName;

    private List<String> ingredients;

    private int maxPeople;

    private String recruitDate;

    private String writerEmail;

    private String title;

    private String content;

    public Recruit toEntity(UserService userService) {
        User writer = userService.getUserByEmail(writerEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + writerEmail));

        return Recruit.builder()
                .foodName(foodName)
                .ingredients(ingredients)
                .maxPeople(maxPeople)
                .recruitDate(recruitDate)
                .writer(writer)
                .title(title)
                .content(content)
                .build();
    }
}
