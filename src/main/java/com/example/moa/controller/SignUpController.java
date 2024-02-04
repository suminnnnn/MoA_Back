package com.example.moa.controller;

import com.example.moa.dto.user.SignUpDto;
import com.example.moa.service.user.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        loginService.join(signUpDto);
        return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.CREATED); //201
    }

    @GetMapping("/validation")
    public ResponseEntity<ApiResponse> validateUser(HttpServletRequest request, @RequestParam String email) {
        loginService.isEmailDuplicate(email);

        return ResponseEntity.ok()
                .body(new ApiResponse("사용가능한 e-mail 입니다.", "200"));
    }
}