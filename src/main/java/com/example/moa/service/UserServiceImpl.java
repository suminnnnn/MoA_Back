package com.example.moa.service;


import com.example.moa.domain.Recruit;
import com.example.moa.domain.User;
import com.example.moa.dto.SignUpDto;
import com.example.moa.exception.DuplicateEmailException;
import com.example.moa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private final UserRepository userRepository;
    @Override
    public User join (SignUpDto signUpDto) {
        return userRepository.save(signUpDto.toEntity());
    }

    @Override
    public void isEmailDuplicate(String email) {
        userRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new DuplicateEmailException("Duplicate email");
                });
    }

    @Override
    public Optional<User> getUserByEmail(String Email){
        return userRepository.findByEmail(Email);
    }

    @Override
    public List<Recruit> getRecruitmentsByUser(String writerEmail) {
        User user = userRepository.findById(writerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid writer Email"));

        return user.getRecruitments();
    }
}
