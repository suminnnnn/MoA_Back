package com.example.moa.controller;

import com.example.moa.domain.User;
import com.example.moa.repository.UserRepository;
import com.example.moa.service.UserService;
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
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {

        Optional<User> user1 = userService.getUserByEmail(user.getEmail());
        if (user1.isEmpty() || !user.getPassword().equals(user.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse("ID, PW를 확인하세요", "400"));
        }
        return ResponseEntity.ok(new ApiResponse("로그인에 성공했습니다.", "200"));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody User user){
        User user1 = userService.registerUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{Email}")
                .buildAndExpand(user.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse("회원가입이 완료되었습니다.", "201"));
    }








}
