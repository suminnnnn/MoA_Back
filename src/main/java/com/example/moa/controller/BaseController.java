package com.example.moa.controller;

import com.example.moa.jwt.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

    @Autowired
    protected JwtTokenUtil jwtTokenUtil;

    protected String getEmailFromToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7); // "Bearer " 제거

        return jwtTokenUtil.getEmailFromToken(token);
    }

}
