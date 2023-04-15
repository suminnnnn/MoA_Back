package com.example.moa.service.base;

import com.example.moa.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public interface BaseService {

    String getEmailFromToken(HttpServletRequest httpServletRequest);
    ResponseCookie csrfCookie (HttpServletRequest httpServletRequest);
    String generateJwt(User user);
}
