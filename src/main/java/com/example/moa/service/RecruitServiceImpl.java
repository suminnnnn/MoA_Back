package com.example.moa.service;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.RecruitUser;
import com.example.moa.domain.Role;
import com.example.moa.domain.User;
import com.example.moa.dto.RecruitModifyDto;
import com.example.moa.dto.RecruitRequestDto;
import com.example.moa.dto.RecruitResponseDto;
import com.example.moa.dto.RecruitUserDto;
import com.example.moa.exception.DuplicateEmailException;
import com.example.moa.repository.RecruitRepository;
import com.example.moa.repository.RecruitUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitServiceImpl implements RecruitService{
    @Autowired
    private final RecruitRepository recruitRepository;

    @Autowired
    private final RecruitUserRepository recruitUserRepository;

    @Autowired
    private final UserService userService;

    @Override
    public Recruit saveRecruit(RecruitRequestDto requestDto) {

        User writer = userService.getUserByEmail(requestDto.getWriterEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + requestDto.getWriterEmail()));

       return recruitRepository.save(requestDto.toEntity(writer));
    }

    @Override
    public RecruitUser saveRecruitUser(RecruitUserDto recruitUserDto, Role role) {
        Recruit recruit = this.getRecruitById(recruitUserDto.getRecruitId())
                .orElseThrow( () -> new IllegalArgumentException("Invalid recruit Id"));

        User user = userService.getUserByEmail(recruitUserDto.getWriterEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email"));

        recruit.addParticipatePeople();
        return recruitUserRepository.save(recruitUserDto.toEntity(user, recruit, role));
    }
    @Override
    public RecruitUser saveRecruitUser(RecruitUser recruitUser) {
        Recruit recruit = recruitUser.getRecruit();
        recruit.addParticipatePeople();
        return recruitUserRepository.save(recruitUser);
    }

    @Override
    public void participationDuplicate(RecruitUserDto recruitUserDto) {

        Recruit recruit = this.getRecruitById(recruitUserDto.getRecruitId())
                .orElseThrow( () -> new IllegalArgumentException("Invalid recruit Id"));

        List<RecruitUser> recruitUsers= recruitUserRepository.findByRecruit(recruit);
        recruitUsers.stream()
                .filter(recruitUser -> recruitUser.getUser().getEmail().equals(recruitUserDto.getWriterEmail()))
                .findFirst()
                .ifPresent(m -> {
                    throw new DuplicateEmailException("Duplicate participate");
                });
    }

    @Override
    public boolean isMaxPeople(RecruitUserDto recruitUserDto){
        Recruit recruit = recruitRepository.findByRecruitId(recruitUserDto.getRecruitId());
        return recruit.getMaxPeople() > recruit.getParticipatePeople() ? true : false ;
    }

    @Override
    public Recruit update(RecruitModifyDto recruitModifyDto){
        Recruit recruit = recruitRepository.findByRecruitId(recruitModifyDto.getRecruitId());
        return recruit.update(recruitModifyDto);
    }

    @Override
    public List<RecruitResponseDto> findAllDesc() {
        return recruitRepository.findAll()
                .stream()
                .map(RecruitResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Recruit> getRecruitById(Long id) {
        return recruitRepository.findById(id);
    }
}
