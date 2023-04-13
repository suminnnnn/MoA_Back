package com.example.moa.controller;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.RecruitUser;
import com.example.moa.domain.Role;
import com.example.moa.dto.RecruitModifyDto;
import com.example.moa.dto.RecruitRequestDto;
import com.example.moa.dto.RecruitResponseDto;
import com.example.moa.dto.RecruitUserDto;
import com.example.moa.jwt.JwtTokenUtil;
import com.example.moa.service.RecruitService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruitments")
@RequiredArgsConstructor
public class RecruitController extends BaseController{

    @Autowired
    private final RecruitService recruitService;


    @GetMapping
    public ResponseEntity<List<RecruitResponseDto>> getAllRecruits() {
        List<RecruitResponseDto> recruits = recruitService.findAllDesc();
        return ResponseEntity.ok().body(recruits);
    }

    @PostMapping("/create")
    public ResponseEntity<RecruitResponseDto> createRecruit(HttpServletRequest httpServletRequest, @RequestBody RecruitRequestDto requestDto) {
        String email = getEmailFromToken(httpServletRequest);
        requestDto.setWriterEmail(email);

        Recruit savedRecruit = recruitService.saveRecruit(requestDto);

        recruitService.saveRecruitUser(
                RecruitUser.builder()
                        .id(savedRecruit.getRecruitId())
                        .recruit(savedRecruit)
                        .user(savedRecruit.getWriter())
                        .role(Role.ADMIN)
                        .build()
        );

        return ResponseEntity.ok().body(RecruitResponseDto.from(savedRecruit));
    }

    @PostMapping("/participate")
    public ResponseEntity<?> participateRecruit(HttpServletRequest httpServletRequest, @RequestBody RecruitUserDto recruitUserDto){
        String email = getEmailFromToken(httpServletRequest);
        recruitUserDto.setWriterEmail(email);

        if(!recruitService.isMaxPeople(recruitUserDto)){
            return ResponseEntity.badRequest().body("인원 초과입니다.");
        }
        recruitService.participationDuplicate(recruitUserDto);
        recruitService.saveRecruitUser(recruitUserDto,Role.USER);
        return new ResponseEntity<>("참여 신청 완료 되었습니다.", HttpStatus.CREATED); //201
    }

    @PostMapping("/modify")
    public ResponseEntity<RecruitResponseDto>  modifyRecruit(HttpServletRequest httpServletRequest, @RequestBody RecruitModifyDto modifyDto){
        String email = getEmailFromToken(httpServletRequest);
        modifyDto.setWriterEmail(email);

        Recruit updateRecruit = recruitService.update(modifyDto);
        return ResponseEntity.ok().body(RecruitResponseDto.from(updateRecruit));
    }

}
