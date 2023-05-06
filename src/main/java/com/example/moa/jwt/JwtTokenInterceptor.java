package com.example.moa.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "x-access-token";
    private final JwtTokenUtil jwtTokenUtil;

    public JwtTokenInterceptor(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        JSONObject jsonObj = new JSONObject(authorizationHeader);
        String requestJwt = jsonObj.getString("jwtToken");

        if (requestJwt != null) {
            if (jwtTokenUtil.validateToken(requestJwt)) {
                return true;
            }
        }

        // 검증 실패시 401 Unauthorized 에러 반환
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");

        return false;
    }
}

