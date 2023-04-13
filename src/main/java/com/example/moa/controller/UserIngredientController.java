package com.example.moa.controller;

import com.example.moa.dto.ingredient.IngredientRequestDto;
import com.example.moa.service.user.UserIngredientService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/ingredient")
public class UserIngredientController {
    @Autowired
    private final UserIngredientService userIngredientService;



    //1. 재료 사진 등록 -> 서버에 이미지 파일 저장 -> url 리턴
    @PostMapping("/image")
    public ResponseEntity<?> ingredientImage(@RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        if (file == null) {
            return new ResponseEntity<>("재료 파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        String url =userIngredientService.uploadImage(file);

        List<String> foodLabels = userIngredientService.getLabelsFromImage(url);

        return new ResponseEntity<>(foodLabels, HttpStatus.OK);
    }

    //2. 영수증 사진 등록 -> 서버에 이미지 파일 저장 -> url 리턴
    @PostMapping("/receiptImage")
    public ResponseEntity<?> receiptImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<>("영수증 파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        String url = userIngredientService.uploadReceiptImage(file);

        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerInUser(HttpServletRequest httpServletRequest, @RequestBody IngredientRequestDto ingredientDto){
        String email = userIngredientService.getEmailFromToken(httpServletRequest);
        ingredientDto.setUserEmail(email);
        userIngredientService.registerUser(ingredientDto);

        return new ResponseEntity<>("재료 등록이 완료되었습니다.", HttpStatus.CREATED); //201
    }



}