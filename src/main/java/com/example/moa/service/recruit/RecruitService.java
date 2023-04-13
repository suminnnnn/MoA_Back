package com.example.moa.service.recruit;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.RecruitUser;
import com.example.moa.dto.recruit.RecruitModifyDto;
import com.example.moa.dto.recruit.RecruitRequestDto;
import com.example.moa.dto.recruit.RecruitResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface RecruitService {
    Recruit saveRecruit(RecruitRequestDto requestDto);
    RecruitUser saveRecruitAdmin(Recruit savedRecruit);
    Recruit update(RecruitModifyDto recruitModifyDto);
    List<RecruitResponseDto> findAllDesc();
    Optional<Recruit> getRecruitById(Long id);

    String getEmailFromToken(HttpServletRequest request);
}
