package com.nickfrundt.record_catalog;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
}
