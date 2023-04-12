package com.example.moa.controller;

import com.example.moa.domain.User;
import com.example.moa.jwt.AuthResponse;
import com.example.moa.dto.UserDto;
import com.example.moa.jwt.JwtTokenUtil;
import com.example.moa.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LogInController extends BaseController{
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody UserDto userDto) {
        User user = authService.authenticate(userDto);
        final String token = jwtTokenUtil.generateToken(user);

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        return ResponseEntity.ok()
                .header(csrfToken.getHeaderName(), csrfToken.getToken())
                .body(new AuthResponse(token));
    }
}
