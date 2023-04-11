package com.example.moa.service;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.User;
import com.example.moa.dto.IngredientDto;
import com.example.moa.dto.UserDto;
import com.example.moa.repository.IngredientRepository;
import com.example.moa.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UserRepository userRepository;

    public String uploadReceiptImage(MultipartFile multipartFile) throws IOException {
        // 2. 서버에 파일 저장 & DB에 파일 정보(fileinfo) 저장
        // - 동일 파일명을 피하기 위해 random값 사용
        String originalFilename = multipartFile.getOriginalFilename();
        System.out.println("originalFilename = " + originalFilename);
        //String saveFileName = createSaveFileName(originalFilename);

        String url = "/Users/tlsss/Desktop/" + originalFilename;

        // 2-1.서버에 파일 저장
        multipartFile.transferTo(new File(url));

        return url;
    }

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        // 2. 서버에 파일 저장 & DB에 파일 정보(fileinfo) 저장
        // - 동일 파일명을 피하기 위해 random값 사용
        String originalFilename = multipartFile.getOriginalFilename();
        //String saveFileName = createSaveFileName(originalFilename);

        String url = "/Users/tlsss/Desktop/" + originalFilename;

        // 2-1.서버에 파일 저장
        multipartFile.transferTo(new File(url));

        return url;
    }


    public void register(IngredientDto ingredientDto, User user) {
        LocalDate now = LocalDate.now();

        Ingredient ingredient = Ingredient.builder()
                .name(ingredientDto.getName())
                .user(user)
                .registeredDate(now)
                .purchasedDate(ingredientDto.getPurchasedDate())
                .expirationDate(ingredientDto.getExpirationDate())
                .build();

        ingredientRepository.save(ingredient);
    }
}