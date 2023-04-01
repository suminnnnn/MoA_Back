package com.example.moa.controller;

import com.example.moa.domain.User;
import com.example.moa.dto.LogInDto;
import com.example.moa.dto.SignUpDto;
import com.example.moa.repository.UserRepository;
import com.example.moa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody SignUpDto userDto){
        userService.join(userDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{Email}")
                .buildAndExpand(userDto.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse("회원가입이 완료되었습니다.", "201"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LogInDto userDto) {

        Optional<User> user1 = userService.getUserByEmail(user.getEmail());
        if (user1.isEmpty() || !user.getPassword().equals(user.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse("ID, PW를 확인하세요", "400"));
        }
        return ResponseEntity.ok(new ApiResponse("로그인에 성공했습니다.", "200"));
    }


}
