package com.example.moa.service;

import com.example.moa.domain.User;
import com.example.moa.dto.UserDto;
import com.example.moa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public User authenticate(UserDto userdto) {
        User user = userRepository.findByEmail(userdto.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Invalid email or password"));

        System.out.println(user.getPassword());
        System.out.println(userdto.getPassword());
        if (!passwordEncoder().matches(userdto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return user;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
