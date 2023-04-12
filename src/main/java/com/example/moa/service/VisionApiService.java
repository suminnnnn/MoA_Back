package com.example.moa.service;

//import autovalue.shaded.kotlinx.metadata.internal.protobuf.ByteString;
import com.google.protobuf.ByteString;
import com.google.cloud.vision.v1.*;

//import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;

import com.google.api.gax.core.FixedCredentialsProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisionApiService {
    public List<String> detectLabels(String imageUrl) throws Exception {


        ImageAnnotatorClient vision = ImageAnnotatorClient.create();

        // Load the image from the specified URL
        Path path = Paths.get(imageUrl);
        byte[] data = Files.readAllBytes(path);
        ByteString imgBytes = ByteString.copyFrom(data);

        // Create the image request
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        List<AnnotateImageRequest> requests = new ArrayList<>();
        requests.add(request);

        // Call the Vision API and retrieve the response
        BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
        List<AnnotateImageResponse> responses = response.getResponsesList();
        List<String> labels = new ArrayList<>();
        for (AnnotateImageResponse res : responses) {
            if (res.hasError()) {
                throw new Exception(res.getError().getMessage());
            }
            for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                labels.add(annotation.getDescription());
            }
        }

        // Close the Vision API client
        vision.close();

        return labels;
    }
}
