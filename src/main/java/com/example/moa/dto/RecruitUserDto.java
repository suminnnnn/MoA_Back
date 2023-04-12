package com.example.moa.dto;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.RecruitUser;
import com.example.moa.domain.Role;
import com.example.moa.domain.User;
import com.example.moa.service.RecruitService;
import com.example.moa.service.UserService;
import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RecruitUserDto {

    private Long recruitId;
    private String writerEmail;

    public RecruitUser toEntity(User user, Recruit recruit, Role role)
    {
        return RecruitUser.builder()
                .recruit(recruit)
                .user(user)
                .role(role)
                .build();
    }

}
