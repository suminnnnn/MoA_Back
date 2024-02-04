package com.example.moa.controller;

import com.example.moa.dto.ingredient.IngredientImageDto;
import com.example.moa.dto.ingredient.IngredientRequestDto;
import com.example.moa.dto.ingredient.ReceiptImageDto;
import com.example.moa.service.user.UserIngredientService;
import io.grpc.ManagedChannel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/ingredient")
public class UserIngredientController {

    private final UserIngredientService userIngredientService;

    @PostMapping("/image")
    public ResponseEntity<?> ingredientImage(@RequestParam(value = "file",required = false) MultipartFile file) {

        IngredientImageDto ingredientImageDto = new IngredientImageDto();

        String url = null;
        try {
            url = userIngredientService.uploadImage(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> result = null;
        try {
            result = userIngredientService.translateWords(userIngredientService.getLabelsFromImage(url), "en", "ko");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ingredientImageDto.setIngredientImage(url);
        ingredientImageDto.setResult(result);

        return new ResponseEntity<>(ingredientImageDto, HttpStatus.OK);
    }

    @PostMapping("/receiptImage")
    public ResponseEntity<?> receiptImage(@RequestParam("file") MultipartFile file) {
        String url = null;
        try {
            url = userIngredientService.uploadReceiptImage(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReceiptImageDto receiptImageDto = new ReceiptImageDto();

        receiptImageDto.setReceiptImage(url);

        return new ResponseEntity<>(receiptImageDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerInUser(HttpServletRequest httpServletRequest, @RequestBody IngredientRequestDto ingredientDto){
        Optional<String> email = Optional.ofNullable((String) httpServletRequest.getAttribute("email"));
        ingredientDto.setUserEmail(email);
        userIngredientService.registerUserIngredient(ingredientDto);

        return new ResponseEntity<>("재료 등록이 완료되었습니다.", HttpStatus.CREATED);
    }

}