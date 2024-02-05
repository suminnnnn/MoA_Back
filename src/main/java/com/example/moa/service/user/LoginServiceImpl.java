package com.example.moa.service.user;

import com.example.moa.domain.User;
import com.example.moa.dto.user.SignUpDto;
import com.example.moa.dto.user.UserDto;
import com.example.moa.exception.DuplicateEmailException;
import com.example.moa.jwt.JwtTokenUtil;
import com.example.moa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public User join (SignUpDto signUpDto) {
        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
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
    public User authenticate(UserDto userdto) {
        User user = userRepository.findByEmail(userdto.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(userdto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return user;
    }

    @Override
    public String generateJwt(User user){
        return jwtTokenUtil.generateToken(user);
    }


}
