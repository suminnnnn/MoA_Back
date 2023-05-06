package com.example.moa.service.user;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.User;
import com.example.moa.dto.ingredient.IngredientRequestDto;
import com.example.moa.exception.UserNoIngredientException;
import com.example.moa.repository.IngredientRepository;
import com.example.moa.repository.UserRepository;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserIngredientServiceImpl implements UserIngredientService {

    @Autowired
    private final IngredientRepository ingredientRepository;
    @Autowired
    private final UserRepository userRepository;


    @Autowired
    private Translate translate;

    public String uploadReceiptImage(MultipartFile multipartFile){
        if(multipartFile.isEmpty())
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        String originalFilename = multipartFile.getOriginalFilename();

        //서버에 사진을 저장할 경로 지정
        String url = "C:/Users/tlsss/Desktop/imageMoa/receipt/" + originalFilename;

        try {
            multipartFile.transferTo(new File(url));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;
    }

    public String uploadImage(MultipartFile multipartFile) {
        if(multipartFile.isEmpty())
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        String originalFilename = multipartFile.getOriginalFilename();

        //서버에 사진을 저장할 경로 지정
        String url = "C:/Users/tlsss/Desktop/imageMoa/ingredient/" + originalFilename;

        try {
            multipartFile.transferTo(new File(url));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;
    }

    public List<String> getLabelsFromImage(String imgFilePath) {

        AtomicReference<String> labels = new AtomicReference<>("");

        try {
            ImageAnnotatorClient client = ImageAnnotatorClient.create();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> result = null;

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            // Reads the image file into memory
            Path path = Paths.get(imgFilePath);
            byte[] data = Files.readAllBytes(path);
            ByteString imgBytes = ByteString.copyFrom(data);

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            AnnotateImageRequest request =
                    AnnotateImageRequest.newBuilder()
                            .addFeatures(feat)
                            .setImage(img)
                            .build();
            requests.add(request);

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            result = new ArrayList<>();

            for (AnnotateImageResponse res : responses) {
                for (EntityAnnotation annotation : res.getLabelAnnotationsList())
                    result.add(annotation.getDescription());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> translateWords(List<String> words, String sourceLanguage, String targetLanguage) {
        List<String> translations = new ArrayList<>();
        for (String word : words) {
            String translation = translate(word, sourceLanguage, targetLanguage);
            translations.add(translation);
        }
        return translations;
    }

    @Override
    public String translate(String text, String sourceLanguage, String targetLanguage) {
        Translation translation = translate.translate(text, Translate.TranslateOption.sourceLanguage(sourceLanguage), Translate.TranslateOption.targetLanguage(targetLanguage));
        return translation.getTranslatedText();
    }

    @Override
    public Ingredient registerUser(IngredientRequestDto ingredientDto) {
        User user = userRepository.findByEmail(ingredientDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + ingredientDto.getUserEmail()));
        
        Ingredient savedIngredient = ingredientDto.toEntity(user);

        return ingredientRepository.save(savedIngredient);
    }

}
