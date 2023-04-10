package com.example.moa.controller;

import com.example.moa.domain.Recruit;
import com.example.moa.dto.RecruitRequestDto;
import com.example.moa.dto.RecruitResponseDto;
import com.example.moa.service.RecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruitments")
@RequiredArgsConstructor
public class RecruitController {

    @Autowired
    private final RecruitService recruitService;

    @GetMapping
    public ResponseEntity<List<RecruitResponseDto>> getAllRecruits() {
        List<RecruitResponseDto> recruits = recruitService.findAllDesc();
        return ResponseEntity.ok().body(recruits);
    }

    @PostMapping("/create")
    public RecruitResponseDto createRecruit(@RequestBody RecruitRequestDto requestDto) {
        Recruit savedRecruit = recruitService.savRecruit(requestDto);
        return RecruitResponseDto.from(savedRecruit);
    }

}
