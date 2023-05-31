package com.example.moa.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private String name;
    private Optional<String> userEmail;
    private String birth;
}
