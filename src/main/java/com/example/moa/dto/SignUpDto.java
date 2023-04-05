package com.example.moa.dto;

import com.example.moa.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    private String email;
    private String password;
    private String gender;
    private String name;
    private String birth;

    public User toEntity() {
       return User.builder()
               .email(email)
               .password(password)
               .gender(gender)
               .name(name)
               .birth(birth)
               .build();
    }

}
