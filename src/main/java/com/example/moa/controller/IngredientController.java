package com.example.moa.controller;

import com.example.moa.domain.User;
import com.example.moa.dto.IngredientDto;
import com.example.moa.dto.IngredientImageDto;
import com.example.moa.dto.ReceiptImageDto;
import com.example.moa.service.IngredientService;
import com.example.moa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private UserService userService;

    //1. 재료 사진 등록 -> 서버에 이미지 파일 저장 -> url 리턴
    @PostMapping("/image")
    public ResponseEntity<?> ingredientImage(@RequestParam(value = "file",required = false) MultipartFile file) throws Exception {
        if (file == null) {
            throw new IOException();
        }
        IngredientImageDto ingredientImageDto = new IngredientImageDto();

        String url = ingredientService.uploadImage(file);
        List<String> result = ingredientService.translateWords(ingredientService.getLabelsFromImage(url), "en", "ko");

        ingredientImageDto.setIngredientImage(url);
        ingredientImageDto.setResult(result);

        return new ResponseEntity<>(ingredientImageDto, HttpStatus.OK);
    }

    //2. 영수증 사진 등록 -> 서버에 이미지 파일 저장 -> url 리턴
    @PostMapping("/receiptimage")
    public ResponseEntity<?> receiptImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            throw new IOException();
        }

        String url = ingredientService.uploadReceiptImage(file);
        ReceiptImageDto receiptImageDto = new ReceiptImageDto();

        receiptImageDto.setReceiptImage(url);

        return new ResponseEntity<>(receiptImageDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerIngredient(@RequestBody IngredientDto ingredientDto, @AuthenticationPrincipal User user){
        //System.out.println("user:"+user);
        ingredientService.register(ingredientDto, user);

        return new ResponseEntity<>("재료 등록이 완료되었습니다.", HttpStatus.CREATED); //201
    }

}
