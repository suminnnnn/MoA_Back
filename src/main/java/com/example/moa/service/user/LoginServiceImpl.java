package com.example.moa.service.user;

import com.example.moa.domain.User;
import com.example.moa.dto.user.SignUpDto;
import com.example.moa.dto.user.UserDto;
import com.example.moa.exception.DuplicateEmailException;
import com.example.moa.repository.UserRepository;
import com.example.moa.service.base.BaseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final BaseService baseService;

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
    public ResponseCookie makeCsrf(HttpServletRequest request){
        return baseService.csrfCookie(request);
    }

    @Override
    public String generateJwt(User user){
        return baseService.generateJwt(user);
    }


}
