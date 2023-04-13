package com.example.moa.service.recruit;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.RecruitUser;
import com.example.moa.domain.Role;
import com.example.moa.dto.ingredient.IngredientResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface RecruitParticipateService {
    RecruitUser saveRecruitUser(Long id, String email, Role role);
    void participationDuplicate(Long id, String email);
    boolean isMaxPeople(Long id);
    String getEmailFromToken(HttpServletRequest request);
    List<IngredientResponseDto> getIngredientsByEmail(String email);
}
