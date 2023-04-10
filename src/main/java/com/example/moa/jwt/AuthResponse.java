package com.example.moa.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class AuthResponse {
    private final String jwtToken;

    public AuthResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
