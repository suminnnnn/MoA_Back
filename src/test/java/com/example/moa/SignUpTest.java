package com.example.moa;

import com.example.moa.domain.User;
import com.example.moa.dto.user.SignUpDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class SignUpTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void signUpTest() throws Exception {
        //Given
        SignUpDto user = SignUpDto.builder()
                .email("shin@ajou.ac.kr")
                .password("1212")
                .gender("Female")
                .name("Shin-sumin")
                .build();

        User findUser = userService.join(user);
        Assertions.assertEquals(user.getName(), findUser.getName());
    }

}
