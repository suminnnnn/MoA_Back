package com.example.moa.controller;

import com.example.moa.domain.User;
import com.example.moa.service.AuthService;
import com.example.moa.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenUtil {
    private String secretKey = "tlstnalsdjelTdjskwhffuqorhvkdlrjakwsmsrjdlagkrltlfgekwlsWk";
    private Key key;
    private long tokenValidTime = 30 * 60 * 1000L;
    Date now = new Date();

    //Base64로 인코딩
    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000);
        Claims claims = Jwts.claims().setSubject(user.getEmail());

        claims.put("userEmail", user.getEmail());

        String jwt = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .signWith(key, SignatureAlgorithm.HS256)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
        return jwt;
    }
}
