package com.belhard.resourceservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class ResourceServiceApplication {

    @Value("${S3_URL}")
    private String s3ClientUrl;

    @Value("${S3_ACCESS_KEY}")
    private String s3ClientAccessKey;

    @Value("${S3_SECRET_KEY}")
    private String s3ClientSecretKey;



    public static void main(String[] args) {
        SpringApplication.run(ResourceServiceApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.EU_NORTH_1)
                .endpointOverride(URI.create(s3ClientUrl))
                .forcePathStyle(true)
                .httpClientBuilder(
                        ApacheHttpClient.builder()
                                .maxConnections(20)
                                .connectionTimeout(Duration.of(5, ChronoUnit.SECONDS))
                )
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(s3ClientAccessKey, s3ClientSecretKey)
                        )
                )
                .build();
    }
}
