package com.belhard.resourceservice.client;

import java.io.IOException;

public interface StorageS3Client {
    void upload(byte[] content, String key);

    byte[] download(String key) throws IOException;

    void delete(String key);
}
