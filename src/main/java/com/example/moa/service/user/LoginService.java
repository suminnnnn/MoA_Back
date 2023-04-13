package com.example.moa.service.user;

import com.example.moa.domain.User;
import com.example.moa.dto.user.SignUpDto;
import com.example.moa.dto.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public interface LoginService{

    User authenticate(UserDto userdto);
    User join(SignUpDto signUpDto);
    void isEmailDuplicate(String email);
    ResponseCookie makeCsrf(HttpServletRequest request);
    String generateJwt(User user);
}
