package com.example.moa.dto.recruit;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.Recruit;
import com.example.moa.domain.User;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RecruitCreateResponseDto {

    private Long id;

    private String foodName;

    private List<String> needIngredients;

    private int maxPeople;

    private String recruitDate;
    // 모임 날짜

    private String createdAt;
    //작성 날짜

    private String writerEmail;

    private String title;

    private String content;

    private String roomId;
    public static RecruitCreateResponseDto from(Recruit recruit){

        User writer = recruit.getWriter();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return RecruitCreateResponseDto.builder()
                .id(recruit.getId())
                .foodName(recruit.getFoodName())
                .needIngredients(recruit.getNeedIngredients())
                .maxPeople(recruit.getMaxPeople())
                .recruitDate(recruit.getRecruitDate().format(formatter))
                .createdAt(recruit.getCreatedAt().format(formatter))
                .writerEmail(writer.getEmail())
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .roomId(recruit.getChatRoomId())
                .build();
    }
}
