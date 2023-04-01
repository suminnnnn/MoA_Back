package com.example.moa.service;


import com.example.moa.domain.User;
import com.example.moa.dto.SignUpDto;
import com.example.moa.exception.DuplicateEmailException;
import com.example.moa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void join(SignUpDto userDto) {
        userRepository.save(userDto.toEntity());
    }

    public void isEmailDuplicate(SignUpDto userDto) {
        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(m -> {
                    throw new DuplicateEmailException("Duplicate email");
                });
    }

    public Optional<User> getUserByEmail(String Email){
        return userRepository.findByEmail(Email);
    }

}
