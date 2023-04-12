package com.example.moa.controller;

import com.example.moa.domain.User;
import com.example.moa.dto.IngredientDto;
import com.example.moa.service.IngredientService;
import com.example.moa.service.UserService;
import com.example.moa.service.VisionApiService;
import com.google.cloud.vision.v1.EntityAnnotation;
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
    IngredientService ingredientService;

    @Autowired
    UserService userService;

    private final VisionApiService visionApiService;

    public IngredientController(VisionApiService visionApiService) {
        this.visionApiService = visionApiService;
    }

    //1. 재료 사진 등록 -> 서버에 이미지 파일 저장 -> url 리턴
    @PostMapping("/image")
    public ResponseEntity<?> ingredientImage(@RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        if (file == null) {
            return new ResponseEntity<>("재료 파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        String url = ingredientService.uploadImage(file);

        List<String> foodLabels = ingredientService.getLabelsFromImage(url);

        return new ResponseEntity<>(foodLabels, HttpStatus.OK);
    }

    //2. 영수증 사진 등록 -> 서버에 이미지 파일 저장 -> url 리턴
    @PostMapping("/receiptimage")
    public ResponseEntity<?> receiptImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<>("영수증 파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        String url = ingredientService.uploadReceiptImage(file);

        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerIngredient(@RequestBody IngredientDto ingredientDto, @AuthenticationPrincipal User user){
        //System.out.println("user:"+user);
        ingredientService.register(ingredientDto, user);

        return new ResponseEntity<>("재료 등록이 완료되었습니다.", HttpStatus.CREATED); //201
    }


}
