package com.nickfrundt.record_catalog;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nickfrundt.record_catalog.model.ScanResponse;
import com.nickfrundt.record_catalog.model.ScannedRecord;

@Service
public class RecordVisionService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RecordVisionService() {

        String apiKey = System.getenv("OPENAI_API_KEY");

        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public List<ScannedRecord> identifyRecords(MultipartFile image) throws Exception {

        String base64Image =
                Base64.getEncoder().encodeToString(image.getBytes());

        String contentType = image.getContentType();

        String dataUrl =
                "data:" + contentType + ";base64," + base64Image;


        Map<String, Object> schema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "records", Map.of(
                                "type", "array",
                                "items", Map.of(
                                        "type", "object",
                                        "properties", Map.of(
                                                "artist", Map.of(
                                                        "type", "string"
                                                ),
                                                "album", Map.of(
                                                        "type", "string"
                                                ),
                                                "confidence", Map.of(
                                                        "type", "number"
                                                )
                                        ),
                                        "required", List.of(
                                                "artist",
                                                "album",
                                                "confidence"
                                        ),
                                        "additionalProperties", false
                                )
                        )
                ),
                "required", List.of("records"),
                "additionalProperties", false
        );


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
                                                Examine this photo of vinyl record spines.

                                                Identify every record you can reasonably recognize.

                                                For each record:
                                                - identify the artist
                                                - identify the album title
                                                - give a confidence score from 0 to 1

                                                Do not invent records.

                                                If you cannot reasonably identify a spine,
                                                leave it out.
                                                """
                                        ),

                                        Map.of(
                                                "type", "input_image",
                                                "image_url", dataUrl
                                        )
                                )
                        )
                ),

                "text", Map.of(
                        "format", Map.of(
                                "type", "json_schema",
                                "name", "record_scan",
                                "strict", true,
                                "schema", schema
                        )
                )
        );


        Map<?, ?> response = restClient.post()
                .uri("/responses")
                .body(requestBody)
                .retrieve()
                .body(Map.class);


        List<?> output =
                (List<?>) response.get("output");


        for (Object outputItem : output) {

            Map<?, ?> item =
                    (Map<?, ?>) outputItem;

            if ("message".equals(item.get("type"))) {

                List<?> content =
                        (List<?>) item.get("content");

                for (Object contentItem : content) {

                    Map<?, ?> contentMap =
                            (Map<?, ?>) contentItem;

                    if ("output_text".equals(
                            contentMap.get("type"))) {

                        String json =
                                contentMap.get("text").toString();

                        ScanResponse scanResponse =
                                objectMapper.readValue(
                                        json,
                                        ScanResponse.class
                                );

                        return scanResponse.getRecords();
                    }
                }
            }
        }


        return List.of();
    }
}