package com.nickfrundt.record_catalog;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RecordVisionService {

    private final RestClient restClient;

    public RecordVisionService() {

        String apiKey = System.getenv("OPENAI_API_KEY");

        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public String identifyRecords(MultipartFile image) throws Exception {

        String base64Image =
                Base64.getEncoder().encodeToString(image.getBytes());

        String contentType = image.getContentType();

        String dataUrl =
                "data:" + contentType + ";base64," + base64Image;

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-5.6-luna",

                "input", List.of(
                        Map.of(
                                "role", "user",

                                "content", List.of(

                                        Map.of(
                                                "type", "input_text",
                                                "text",
                                                """
                                                Look carefully at this photo of vinyl record spines.

                                                Identify every album you can reasonably recognize.

                                                For now, return a simple readable list
                                                containing the artist and album title.

                                                If you are unsure about a record,
                                                clearly say that you are uncertain.
                                                """
                                        ),

                                        Map.of(
                                                "type", "input_image",
                                                "image_url", dataUrl
                                        )
                                )
                        )
                )
        );

        Map<?, ?> response = restClient.post()
                .uri("/responses")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        return response.toString();
    }
}