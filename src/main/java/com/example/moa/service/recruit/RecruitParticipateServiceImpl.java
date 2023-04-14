package com.example.moa.service.recruit;

import com.example.moa.domain.*;
import com.example.moa.dto.ingredient.IngredientResponseDto;
import com.example.moa.exception.DuplicateEmailException;
import com.example.moa.exception.NotFindRecruitException;
import com.example.moa.exception.UserNoIngredientException;
import com.example.moa.repository.RecruitRepository;
import com.example.moa.repository.RecruitUserRepository;
import com.example.moa.repository.UserRepository;
import com.example.moa.service.base.BaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitParticipateServiceImpl implements RecruitParticipateService{

    @Autowired
    private final RecruitRepository recruitRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RecruitUserRepository recruitUserRepository;
    @Autowired
    private final BaseService baseService;

    @Override
    public RecruitUser saveRecruitUser(Long id, String email, Role role) {
        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("Invalid recruit Id"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email"));

        recruit.addParticipatePeople();
        return recruitUserRepository.save(
                RecruitUser.builder()
                        .recruit(recruit)
                        .user(user)
                        .role(role)
                        .build()
        );
    }
    @Override
    public void participationDuplicate(Long id, String email) {

        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("Invalid recruit Id"));

        List<RecruitUser> recruitUsers= recruitUserRepository.findByRecruit(recruit);
        recruitUsers.stream()
                .filter(recruitUser -> recruitUser.getUser().getEmail().equals(email))
                .findFirst()
                .ifPresent(m -> {
                    throw new DuplicateEmailException("Duplicate participate");
                });
    }

    @Override
    public boolean isMaxPeople(Long id){
        Recruit recruit = recruitRepository.findByRecruitId(id)
                .orElseThrow(()->new NotFindRecruitException(id + " recruit not found"));;
        return recruit.getMaxPeople() > recruit.getParticipatePeople() ? true : false ;
    }
    @Override
    public String getEmailFromToken(HttpServletRequest request){
        return baseService.getEmailFromToken(request);
    }

    @Override
    public List<IngredientResponseDto> getIngredientsByEmail(String email) {
        User user= userRepository.findById(email)
                .orElseThrow(() -> new UserNoIngredientException("no have ingredient"));
        return user.getIngredients()
                .stream()
                .map(IngredientResponseDto::from)
                .collect(Collectors.toList());
    }

}
