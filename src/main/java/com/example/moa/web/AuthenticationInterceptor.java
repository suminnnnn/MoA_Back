package com.example.moa.web;

import com.example.moa.jwt.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        // 토큰에서 email 주소 추출
        String email = extractEmailFromToken(token);
        if (email == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        // 인증 정보 설정
        request.setAttribute("email", email);
        return true;
    }

    private String extractEmailFromToken(String token) {
        // 토큰에서 email 주소 추출하는 로직 구현
        return jwtTokenUtil.getEmailFromToken(token.substring(7));
    }
}

