package com.example.moa.dto;

import com.example.moa.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String password;

//    public UserDto from(User user){
//        return UserDto.builder()
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .build();
//    }
}
