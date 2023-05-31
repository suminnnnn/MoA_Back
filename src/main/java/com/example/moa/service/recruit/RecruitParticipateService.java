package com.example.moa.service.recruit;

import com.example.moa.domain.*;
import com.example.moa.dto.ingredient.IngredientResponseDto;
import com.example.moa.dto.recruit.RecruitUserRequestDto;
import com.example.moa.dto.recruit.RecruitUserResponseDto;

import java.util.List;

public interface RecruitParticipateService {
    void saveRecruitUser(RecruitUserRequestDto participateDto);

    List<RecruitUserResponseDto> showParticipateList(Long id);
    void allowRecruitUser(Long id);
    void participationDuplicate(Recruit recruit, User user);
    boolean isMaxPeople(Long id);
    List<IngredientResponseDto> getIngredientsByEmail(String email);
}
