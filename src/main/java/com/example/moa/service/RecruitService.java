package com.example.moa.service;

import com.example.moa.domain.Recruit;
import com.example.moa.dto.RecruitRequestDto;
import com.example.moa.dto.RecruitResponseDto;

import java.util.List;

public interface RecruitService {
    Recruit savRecruit(RecruitRequestDto requestDto);
    List<RecruitResponseDto> findAllDesc();
}
