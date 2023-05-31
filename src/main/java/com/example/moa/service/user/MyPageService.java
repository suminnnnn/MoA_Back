package com.example.moa.service.user;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.Reservation;
import com.example.moa.dto.user.UserInfoDto;

import java.util.List;
import java.util.Optional;

public interface MyPageService {
    List<Ingredient> getAllIngredients(String email);
    List<Reservation> getAllReservations(String email);
    UserInfoDto getUserInfos(String email);
}
