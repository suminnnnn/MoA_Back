package com.example.moa.controller;

import com.example.moa.dto.ingredient.IngredientImageDto;
import com.example.moa.dto.ingredient.IngredientRequestDto;
import com.example.moa.dto.ingredient.ReceiptImageDto;
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

    @PostMapping("/image")
    public ResponseEntity<?> ingredientImage(@RequestParam(value = "file",required = false) MultipartFile file){
        if (file == null) {
            return new ResponseEntity<>("재료 파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        IngredientImageDto ingredientImageDto = new IngredientImageDto();

        String url = userIngredientService.uploadImage(file);
        List<String> result = userIngredientService.translateWords(userIngredientService.getLabelsFromImage(url), "en", "ko");

        ingredientImageDto.setIngredientImage(url);
        ingredientImageDto.setResult(result);

        return new ResponseEntity<>(ingredientImageDto, HttpStatus.OK);
    }

    //2. 영수증 사진 등록 -> 서버에 이미지 파일 저장 -> url 리턴
    @PostMapping("/receiptImage")
    public ResponseEntity<?> receiptImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<>("영수증 파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        String url = userIngredientService.uploadReceiptImage(file);
        ReceiptImageDto receiptImageDto = new ReceiptImageDto();

        receiptImageDto.setReceiptImage(url);

        return new ResponseEntity<>(receiptImageDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerInUser(HttpServletRequest httpServletRequest, @RequestBody IngredientRequestDto ingredientDto){
        String email = userIngredientService.getEmailFromToken(httpServletRequest);
        ingredientDto.setUserEmail(email);
        userIngredientService.registerUser(ingredientDto);

        return new ResponseEntity<>("재료 등록이 완료되었습니다.", HttpStatus.CREATED); //201
    }



}