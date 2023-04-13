package com.example.moa.dto.recruit;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class RecruitModifyDto extends RecruitRequestDto {
    private Long recruitId;
}
