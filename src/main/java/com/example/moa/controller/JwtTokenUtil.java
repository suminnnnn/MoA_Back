package com.example.moa.controller;

import com.example.moa.domain.User;
import com.example.moa.service.AuthService;
import com.example.moa.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenUtil {
    private String secretKey = "secretKey";

    public String generateToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000);
        Claims claims = Jwts.claims().setSubject(user.getEmail());

        claims.put("userEmail", user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
