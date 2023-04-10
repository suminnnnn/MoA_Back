package com.example.moa.dto;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RecruitResponseDto {

    private Long id;

    private String foodName;

    private List<String> ingredients;

    private int maxPeople;

    private String recruitDate;
    // 모임 날짜

    private String createdAt;
    //작성 날짜

    private String writerEmail;

    private String title;

    private String content;

    public static RecruitResponseDto from(Recruit recruit){

        User writer = recruit.getWriter();

        return RecruitResponseDto.builder()
                .id(recruit.getId())
                .foodName(recruit.getFoodName())
                .ingredients(recruit.getIngredients())
                .maxPeople(recruit.getMaxPeople())
                .recruitDate(recruit.getRecruitDate())
                .createdAt(recruit.getCreatedAt())
                .writerEmail(writer.getEmail())
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .build();
    }
}
