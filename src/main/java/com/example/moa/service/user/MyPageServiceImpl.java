package com.example.moa.service.user;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.Reservation;
import com.example.moa.domain.User;
import com.example.moa.dto.user.UserInfoDto;
import com.example.moa.repository.IngredientRepository;
import com.example.moa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    @Autowired
    private final IngredientRepository ingredientRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public List<Ingredient> getAllIngredients(Optional<String> email) {
        User user = userRepository.findByEmail(String.valueOf(email))
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + email));
        return user.getIngredients();
    }

    @Override
    public List<Reservation> getAllReservations(Optional<String> email) {
        User user = userRepository.findByEmail(String.valueOf(email))
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + email));
        return user.getReservations();
    }

    @Override
    public UserInfoDto getUserInfos(String email) {
        User user = userRepository.findByEmail(String.valueOf(email))
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + email));

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .birth(user.getBirth())
                .build();

        return userInfoDto;
    }
}
