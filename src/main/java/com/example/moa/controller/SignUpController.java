package com.example.moa.controller;

import com.example.moa.dto.SignUpDto;
import com.example.moa.service.AuthService;
import com.example.moa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SignUpController {

    @Autowired
    private final UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        signUpDto.setPassword(authService.passwordEncoder().encode(signUpDto.getPassword()));
        userService.join(signUpDto);
        return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.CREATED); //201
    }

//    @GetMapping("/validation")
//    public ResponseEntity<ApiResponse> validateUser(HttpServletRequest request, @RequestParam String email) {
//        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//        return ResponseEntity.ok()
//                .header(csrfToken.getHeaderName(), csrfToken.getToken())
//                .body(new ApiResponse("사용가능한 e-mail 입니다.", "200"));
//    }
//
//    @GetMapping("/csrf")
//    public CsrfToken csrf(CsrfToken token) {
//        return token;
//    }

    @GetMapping("/validation")
    public ResponseEntity<ApiResponse> validateUser(@RequestParam String email) {
        userService.isEmailDuplicate(email);
        return ResponseEntity.ok(new ApiResponse("사용가능한 e-mail 입니다.", "200"));
    }

//    @GetMapping("/validation")
//    public ResponseEntity<ApiResponse> validateUser(HttpServletRequest request, HttpServletResponse response, @RequestParam String email) {
//        // CSRF 토큰 발행
//        CsrfToken csrfToken = csrfTokenRepository.generateToken(request);
//
//        // HTTP 응답의 쿠키에 CSRF 토큰 값을 저장
//        Cookie cookie = new Cookie("XSRF-TOKEN", csrfToken.getToken());
//        cookie.setPath("/");
//        response.addCookie(cookie);
//
//        // HTTP 응답에 CSRF 토큰 값을 포함하여 반환
//        return ResponseEntity.ok()
//                .header(csrfToken.getHeaderName(), csrfToken.getToken())
//                .body(new ApiResponse("사용가능한 e-mail 입니다.", "200"));
//    }
//
//    @PostMapping("/some-action")
//    public ResponseEntity<ApiResponse> someAction(HttpServletRequest request, @RequestParam String someData) {
//        // CSRF 토큰 검증
//        CsrfToken csrfToken = csrfTokenRepository.loadToken(request);
//        if (csrfToken == null) {
//            throw new IllegalStateException("CSRF 토큰을 찾을 수 없습니다.");
//        }
//        if (!csrfToken.getToken().equals(request.getHeader(csrfToken.getHeaderName()))) {
//            throw new IllegalStateException("잘못된 CSRF 토큰입니다.");
//        }
//
//        // someData에 대한 작업 수행
//
//        return ResponseEntity.ok()
//                .body(new ApiResponse("some-action이 수행되었습니다.", "200"));
//    }
}
