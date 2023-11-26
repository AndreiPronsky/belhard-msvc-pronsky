package com.belhard.resourceservice.client.impl;

import com.belhard.resourceservice.client.SongClient;
import com.belhard.resourceservice.service.dto.MetaDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SongClientImpl implements SongClient {

    private final WebClient webClient;

    @Value("${clients.song-service}")
    private String songServiceUrl;

    @Value("?id=")
    private String endpoint;

    @Override
    public void create(MetaDataDto metaDataDto) {
        String response = webClient.post()
                .uri(songServiceUrl)
                .bodyValue(metaDataDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info(response);
    }

    @Override
    public void deleteByIds(List<Long> resourceIds) {
        String response = webClient.delete()
                .uri(buildUri(resourceIds))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info(response);
    }

    private String buildUri(List<Long> ids) {
        String values = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return songServiceUrl + endpoint + values;
    }
}
