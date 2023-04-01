package com.example.moa.dto;

import com.example.moa.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class LogInDto {
    private String email;
    private String password;

}
