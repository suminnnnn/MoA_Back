package com.example.moa.dto.recruit;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RecruitModifyDto extends RecruitCreateRequestDto {
    private Long recruitId;

}
