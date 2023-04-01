package com.example.moa.controller;

import com.example.moa.domain.User;
import com.example.moa.dto.AuthResponse;
import com.example.moa.dto.UserDto;
import com.example.moa.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LogInController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        User user = authService.authenticate(userDto);
        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
