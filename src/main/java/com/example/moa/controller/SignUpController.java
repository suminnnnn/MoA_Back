package com.example.moa.controller;

import com.example.moa.domain.User;
import com.example.moa.dto.UserDto;
import com.example.moa.dto.SignUpDto;
import com.example.moa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SignUpController {

    @Autowired
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        userService.join(signUpDto);

//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/api/users/{Email}")
//                .buildAndExpand(signUpDto.getEmail()).toUri();
        return ResponseEntity.ok(new ApiResponse("회원가입이 완료되었습니다.", "201"));
    }

    @PostMapping("/signup/validation")
    public ResponseEntity<ApiResponse> validateUser(@RequestBody SignUpDto signUpDto) {
        userService.isEmailDuplicate(signUpDto);
        return ResponseEntity.ok(new ApiResponse("사용가능한 e-mail 입니다.", "200"));
    }

}
