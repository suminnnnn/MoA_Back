package com.example.moa.service;

import com.example.moa.domain.Ingredient;
import com.example.moa.domain.User;
import com.example.moa.dto.IngredientDto;
import com.example.moa.dto.UserDto;
import com.example.moa.repository.IngredientRepository;
import com.example.moa.repository.UserRepository;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientService {
    @Value("${google.cloud.vision.api.key}")
    private String apiKey;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UserRepository userRepository;

    public String uploadReceiptImage(MultipartFile multipartFile) throws IOException {
        // 2. 서버에 파일 저장 & DB에 파일 정보(fileinfo) 저장
        String originalFilename = multipartFile.getOriginalFilename();
        //String saveFileName = createSaveFileName(originalFilename);

        String url = "C:/Users/tlsss/Desktop/imageMoa/receipt/" + originalFilename;

        // 2-1.서버에 파일 저장
        multipartFile.transferTo(new File(url));

        return url;
    }

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        // 2. 서버에 파일 저장 & DB에 파일 정보(fileinfo) 저장
        // - 동일 파일명을 피하기 위해 random값 사용
        String originalFilename = multipartFile.getOriginalFilename();
        //String saveFileName = createSaveFileName(originalFilename);

        String url = "C:/Users/tlsss/Desktop/imageMoa/ingredient/" + originalFilename;

        // 2-1.서버에 파일 저장
        multipartFile.transferTo(new File(url));

        return url;
    }

    public List<String> getLabelsFromImage(String imageUrl) throws IOException {
        // Google Cloud Vision API에 요청을 보내고 응답을 분석합니다.
        List<String> labels = new ArrayList<>();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            // 이미지 URL로부터 이미지 데이터를 가져옵니다.
            ByteString imgBytes = ByteString.readFrom(new URL(imageUrl).openStream());

            // 이미지에서 라벨을 감지합니다.
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
            requests.add(request);

            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.err.println("Error: " + res.getError().getMessage());
                    return labels;
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    labels.add(annotation.getDescription());
                }
            }
        }

        return labels;
    }

    public void register(IngredientDto ingredientDto, User user) {
        LocalDate now = LocalDate.now();

        Ingredient ingredient = Ingredient.builder()
                .name(ingredientDto.getName())
                .user(user)
                .registeredDate(now)
                .purchasedDate(ingredientDto.getPurchasedDate())
                .expirationDate(ingredientDto.getExpirationDate())
                .ingredientImage(ingredientDto.getIngredientImage())
                .receiptImage(ingredientDto.getReceiptImage())
                .build();

        ingredientRepository.save(ingredient);
    }
}