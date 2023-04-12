package com.example.moa.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class RecruitModifyDto extends RecruitRequestDto{
    private Long recruitId;
}
