package com.belhard.resourceservice.client.impl;

import com.belhard.resourceservice.client.SongClient;
import com.belhard.resourceservice.service.dto.MetaDataDto;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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

    private final EurekaClient eurekaClient;
    private final WebClient webClient;
    private final LoadBalancerClient loadBalancerClient;

    @Value("${SONG_SERVICE_URL}")
    private String songServiceUrl;

    @Value("?id=")
    private String endpoint;

    @Override
    public void create(MetaDataDto metaDataDto) {
        eurekaClient.getApplication(songServiceUrl).getInstances().forEach(i ->
                log.info(i.getInstanceId()));
        String response = webClient.post()
                .uri(getServiceInstance()
                        .getUri())
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
                .uri(buildUriForDelete(resourceIds))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info(response);
    }

    private String buildUriForDelete(List<Long> ids) {
        String values = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return getServiceInstance().getUri() + endpoint + values;
    }

    private ServiceInstance getServiceInstance() {
        return loadBalancerClient.choose(songServiceUrl);
    }
}
