package com.nickfrundt.record_catalog;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.nickfrundt.record_catalog.model.RecordMetadata;

@Service
public class MusicBrainzService {

    private final RestClient restClient;

    public MusicBrainzService() {

        this.restClient = RestClient.builder()
                .baseUrl("https://musicbrainz.org/ws/2")
                .defaultHeader(
                        "User-Agent",
                        "RecordCatalog/1.0 (frunnick@gmail.com)"
                )
                .build();
    }

    public Map<?, ?> searchAlbum(String artist, String album) {

        String query =
                "artist:\"" + artist + "\" AND releasegroup:\"" + album + "\"";

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/release-group/")
                        .queryParam("query", query)
                        .queryParam("fmt", "json")
                        .queryParam("limit", 5)
                        .build())
                .retrieve()
                .body(Map.class);
    }

    public RecordMetadata findMetadata(String artist, String album) {

        Map<?, ?> response = searchAlbum(artist, album);

        if (response == null) {
            return new RecordMetadata(null, 0, false);
        }

        Object results = response.get("release-groups");

        if (!(results instanceof List<?> groups) || groups.isEmpty()) {
            return new RecordMetadata(null, 0, false);
        }

        Map<?, ?> bestMatch = (Map<?, ?>) groups.get(0);

        int matchScore = 0;

        Object score = bestMatch.get("score");

        if (score instanceof Number number) {
            matchScore = number.intValue();
        }

        Integer releaseYear = null;

        Object date = bestMatch.get("first-release-date");

        if (date instanceof String dateString && dateString.length() >= 4) {
            try {
                releaseYear
                        = Integer.parseInt(dateString.substring(0, 4));
            } catch (NumberFormatException e) {
                releaseYear = null;
            }
        }

        boolean verified = matchScore >= 90;

        return new RecordMetadata(
                releaseYear,
                matchScore,
                verified
        );
    }
}
