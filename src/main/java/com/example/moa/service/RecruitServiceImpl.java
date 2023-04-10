package com.example.moa.service;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.User;
import com.example.moa.dto.RecruitRequestDto;
import com.example.moa.dto.RecruitResponseDto;
import com.example.moa.repository.RecruitRepository;
import com.example.moa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitServiceImpl implements RecruitService{
    @Autowired
    private final RecruitRepository recruitRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserService userService;

    @Override
    public Recruit savRecruit(RecruitRequestDto requestDto) {
       return recruitRepository.save(requestDto.toEntity(userService));
    }

    @Override
    public List<RecruitResponseDto> findAllDesc() {
        return recruitRepository.findAll()
                .stream()
                .map(RecruitResponseDto::from)
                .collect(Collectors.toList());
    }
}
