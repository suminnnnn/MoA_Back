package com.example.moa.controller;

import com.example.moa.domain.User;
import com.example.moa.jwt.AuthResponse;
import com.example.moa.dto.user.UserDto;
import com.example.moa.service.user.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class LogInController{

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody UserDto userDto) {
        User user = loginService.authenticate(userDto);
        final String token = loginService.generateJwt(user);
        return ResponseEntity.ok()
                .body(new AuthResponse(token));
    }
}