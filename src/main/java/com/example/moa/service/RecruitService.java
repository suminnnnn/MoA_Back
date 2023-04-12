package com.example.moa.service;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.RecruitUser;
import com.example.moa.domain.Role;
import com.example.moa.dto.RecruitModifyDto;
import com.example.moa.dto.RecruitRequestDto;
import com.example.moa.dto.RecruitResponseDto;
import com.example.moa.dto.RecruitUserDto;

import java.util.List;
import java.util.Optional;

public interface RecruitService {
    Recruit saveRecruit(RecruitRequestDto requestDto);
    Optional<Recruit> getRecruitById(Long Id);
    List<RecruitResponseDto> findAllDesc();

    void participationDuplicate(RecruitUserDto recruitUserDto);
    boolean isMaxPeople(RecruitUserDto recruitUserDto);
    Recruit update(RecruitModifyDto recruitModifyDto);

    RecruitUser saveRecruitUser(RecruitUserDto recruitUserDto, Role role);
    RecruitUser saveRecruitUser(RecruitUser recruitUser);

}
