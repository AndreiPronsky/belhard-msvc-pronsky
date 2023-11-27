package com.belhard.resourceservice.client.impl;

import com.belhard.resourceservice.client.StorageS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.IOException;
import java.util.NoSuchElementException;

@Component
@Slf4j
@RequiredArgsConstructor
public class StorageS3ClientImpl implements StorageS3Client {

    @Value("${S3_BUCKET}")
    private String bucket;
    private final S3Client s3Client;

    @Override
    public void upload(byte[] content, String key) {
        if (!isExistingBucket(bucket)) {
            createBucket(bucket);
        }
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(content));
        log.info("UPLOADED to s3: {}", key);
    }

    @Override
    public byte[] download(String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            return s3Client.getObject(getObjectRequest).readAllBytes();
        } catch (IOException e) {
            throw new NoSuchElementException(e);
        }
    }

    @Override
    public void delete(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
        log.info("DELETED from s3: {}", key);
    }

    private void createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        s3Client.createBucket(createBucketRequest);

        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();

        S3Waiter s3Waiter = s3Client.waiter();

        WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(headBucketRequest);
        waiterResponse.matched().response().map(Object::toString).ifPresent(log::info);
    }

    private boolean isExistingBucket(String bucketName) {
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();
        try {
            s3Client.headBucket(headBucketRequest);
            return true;
        } catch (NoSuchBucketException e) {
            return false;
        }
    }
}
