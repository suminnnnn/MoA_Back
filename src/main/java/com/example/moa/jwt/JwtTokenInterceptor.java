package com.example.moa.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    private final JwtTokenUtil jwtTokenUtil;

    public JwtTokenInterceptor(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            String jwtToken = authorizationHeader.substring(BEARER_TOKEN_PREFIX.length());
            if (jwtTokenUtil.validateToken(jwtToken)) {
                return true;
            }
        }

        // 검증 실패시 401 Unauthorized 에러 반환
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");

        return false;
    }
}

