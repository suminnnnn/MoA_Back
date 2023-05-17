package com.example.moa.service.recruit;

import com.example.moa.domain.Recruit;
import com.example.moa.dto.recruit.RecruitModifyDto;
import com.example.moa.dto.recruit.RecruitCreateRequestDto;
import com.example.moa.dto.recruit.RecruitCreateResponseDto;

import java.util.List;

public interface RecruitService {
    Recruit saveRecruit(RecruitCreateRequestDto requestDto);
    Recruit update(RecruitModifyDto recruitModifyDto);
    List<RecruitCreateResponseDto> findAllDesc();
    RecruitCreateResponseDto showRecruit(Long id);
    void delete(Long id);
    String getChatRoomId(Long id);

    List<RecruitCreateResponseDto> findMyList(String email);

}
