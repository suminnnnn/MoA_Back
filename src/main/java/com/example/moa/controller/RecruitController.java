package com.example.moa.controller;

import com.example.moa.domain.Recruit;
import com.example.moa.dto.recruit.RecruitModifyDto;
import com.example.moa.dto.recruit.RecruitRequestDto;
import com.example.moa.dto.recruit.RecruitResponseDto;
import com.example.moa.service.recruit.RecruitService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitController{

    @Autowired
    private final RecruitService recruitService;


    @GetMapping
    public ResponseEntity<List<RecruitResponseDto>> getAllRecruits() {
        List<RecruitResponseDto> recruits = recruitService.findAllDesc();
        return ResponseEntity.ok().body(recruits);
    }

    @PostMapping("/create")
    public ResponseEntity<RecruitResponseDto> createRecruit(HttpServletRequest httpServletRequest, @RequestBody RecruitRequestDto requestDto) {
        String email = recruitService.getEmailFromToken(httpServletRequest);
        requestDto.setWriterEmail(email);

        Recruit savedRecruit = recruitService.saveRecruit(requestDto);

        recruitService.saveRecruitAdmin(savedRecruit);

        return ResponseEntity.ok().body(RecruitResponseDto.from(savedRecruit));
    }


    @PostMapping("/modify/{id}")
    public ResponseEntity<RecruitResponseDto>  modifyRecruit(HttpServletRequest httpServletRequest, @RequestBody RecruitModifyDto modifyDto){
        String email = recruitService.getEmailFromToken(httpServletRequest);
        modifyDto.setWriterEmail(email);

        Recruit updateRecruit = recruitService.update(modifyDto);
        return ResponseEntity.ok().body(RecruitResponseDto.from(updateRecruit));
    }



}
