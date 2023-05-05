package com.example.moa.service.recruit;

import com.example.moa.domain.*;
import com.example.moa.dto.ingredient.IngredientResponseDto;
import com.example.moa.dto.recruit.RecruitUserDto;

import java.util.List;

public interface RecruitParticipateService {
    void saveRecruitUser(RecruitUserDto participateDto);
    void participationDuplicate(Recruit recruit, User user);
    boolean isMaxPeople(Long id);
    List<IngredientResponseDto> getIngredientsByEmail(String email);
}
