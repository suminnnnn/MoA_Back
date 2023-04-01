package com.example.moa.dto;

import com.example.moa.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
public class SignUpDto {

    private String email;
    private String password;
    private String gender;
    private String name;
    private Date birthDate;

    public User toEntity() {
       return User.builder()
               .email(email)
               .password(password)
               .gender(gender)
               .name(name)
               .birthDate(birthDate)
               .build();
    }

}
