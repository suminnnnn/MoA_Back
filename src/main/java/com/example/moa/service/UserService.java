package com.example.moa.service;


import com.example.moa.domain.User;
import com.example.moa.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String join(User user){
        //같은 이름이 있는 중복 회원x
        validateDuplicateMember(user); //중복 회원 검증
        userRepository.save(user);
        return user.getEmail();
    }

    private void validateDuplicateMember(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public void logIn(String email, String password){
        Optional<User> user = userRepository.findByEmail(email);

        if(!user.isPresent()){
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }

        if(!user.get().getPassword().equals(password)){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }
}
