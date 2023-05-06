package com.example.moa.dto.recruit;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RecruitUserDto {

    private Long recruitId = null;

    private String userEmail=null;

    private List<Long> id;

}
