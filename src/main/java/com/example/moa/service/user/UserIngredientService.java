package com.example.moa.service.user;

import com.example.moa.domain.Ingredient;
import com.example.moa.dto.ingredient.IngredientRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserIngredientService {
    String uploadReceiptImage(MultipartFile multipartFile) throws IOException;
    String uploadImage(MultipartFile multipartFile) throws IOException;
    List<String> getLabelsFromImage(String imgFilePath) throws Exception;
    List<String> translateWords(List<String> words, String sourceLanguage, String targetLanguage);
    String translate(String text, String sourceLanguage, String targetLanguage);
    Ingredient registerUser(IngredientRequestDto ingredientDto);
}
