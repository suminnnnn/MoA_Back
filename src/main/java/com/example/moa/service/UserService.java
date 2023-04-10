package com.example.moa.service;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.User;
import com.example.moa.dto.SignUpDto;
import com.example.moa.exception.DuplicateEmailException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User join(SignUpDto signUpDto);

    void isEmailDuplicate(String email) throws DuplicateEmailException;

    Optional<User> getUserByEmail(String email);
    List<Recruit> getRecruitmentsByUser(String writerEmail);
}
