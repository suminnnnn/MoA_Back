package com.example.moa.controller;

import com.example.moa.domain.User;
import com.example.moa.dto.IngredientDto;
import com.example.moa.dto.UserDto;
import com.example.moa.repository.UserRepository;
import com.example.moa.service.IngredientService;
import com.example.moa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    IngredientService ingredientService;

    @Autowired
    UserService userService;

    //1. 영수증 사진 등록 -> DB에 url 저장
    //2. 재료 사진 등록 -> DB에 url 저장,

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody IngredientDto ingredientDto, @AuthenticationPrincipal User user){
        //System.out.println("user:"+user);
        ingredientService.register(ingredientDto, user);

        return new ResponseEntity<>("재료 등록이 완료되었습니다.", HttpStatus.CREATED); //201
    }


}
