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
    @Autowired
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody UserDto userDto) {
        User user = loginService.authenticate(userDto);
        final String token = loginService.generateJwt(user);

//        ResponseCookie cookie = loginService.makeCsrf(request);

        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new AuthResponse(token));
    }
}