package com.example.moa.service.base;

import com.example.moa.domain.User;
import com.example.moa.jwt.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseServiceImpl implements BaseService{
    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public String getEmailFromToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7); // "Bearer " 제거

        return jwtTokenUtil.getEmailFromToken(token);
    }
    @Override
    public ResponseCookie csrfCookie (HttpServletRequest httpServletRequest){
        CsrfToken csrfToken = (CsrfToken) httpServletRequest.getAttribute(CsrfToken.class.getName());
        return ResponseCookie.from("XSRF-TOKEN", csrfToken.getToken())
                .httpOnly(true)
                .path("/")
                .build();
    }
    @Override
    public String generateJwt(User user){
        return jwtTokenUtil.generateToken(user);
    }
}
