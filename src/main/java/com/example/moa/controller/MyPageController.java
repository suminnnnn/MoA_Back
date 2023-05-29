package com.example.moa.controller;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.Reservation;
import com.example.moa.dto.ingredient.IngredientRequestDto;
import com.example.moa.dto.ingredient.MyIngredientDto;
import com.example.moa.dto.reservation.MyReservationDto;
import com.example.moa.dto.user.UserDto;
import com.example.moa.dto.user.UserInfoDto;
import com.example.moa.service.user.MyPageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private final MyPageService myPageService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> showUserInfos(HttpServletRequest httpServletRequest){
        Optional<String> email = Optional.ofNullable((String) httpServletRequest.getAttribute("email"));

        UserInfoDto infos = myPageService.getUserInfos(email);

        return ResponseEntity.ok().body(infos);
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<MyIngredientDto>> showMyIngredients(HttpServletRequest httpServletRequest){
        Optional<String> email = Optional.ofNullable((String) httpServletRequest.getAttribute("email"));
        List<Ingredient> ingredients = myPageService.getAllIngredients(email);

        List<MyIngredientDto> myIngredientDtos = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            MyIngredientDto myIngredientDto = MyIngredientDto.builder()
                    .name(ingredient.getName())
                    .purchasedDate(ingredient.getPurchasedDate().toString())
                    .expirationDate(ingredient.getExpirationDate().toString())
                    .build();
            myIngredientDtos.add(myIngredientDto);
        }

        return ResponseEntity.ok().body(myIngredientDtos);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<MyReservationDto>> showMyReservations(HttpServletRequest httpServletRequest){
        Optional<String> email = Optional.ofNullable((String) httpServletRequest.getAttribute("email"));
        List<Reservation> reservations = myPageService.getAllReservations(email);

        List<MyReservationDto> myReservationDtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            MyReservationDto myReservationDto = MyReservationDto.builder()
                    .reservationDate(reservation.getReservationDate())
                    .reservationTime(reservation.getReservationTime())
                    .reservationLocation(reservation.getReservationLocation())
                    .build();
            myReservationDtos.add(myReservationDto);
        }

        return ResponseEntity.ok().body(myReservationDtos);
    }
}
