package com.belhard.resourceservice.service.impl;

import com.belhard.resourceservice.client.SongClient;
import com.belhard.resourceservice.client.StorageS3Client;
import com.belhard.resourceservice.data.ResourceRepository;
import com.belhard.resourceservice.data.entities.Resource;
import com.belhard.resourceservice.service.MetaDataService;
import com.belhard.resourceservice.service.ResourceMapper;
import com.belhard.resourceservice.service.ResourceService;
import com.belhard.resourceservice.service.dto.MetaDataDto;
import com.belhard.resourceservice.service.dto.ResourceIdDto;
import com.belhard.resourceservice.service.dto.ResourceIdsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;
    private final MetaDataService metaDataService;
    private final SongClient songClient;
    private final StorageS3Client s3Client;
    private final String folder = "/music/";

    @Override
    public ResourceIdDto upload(byte[] data) {
        String key = String.valueOf(Arrays.hashCode(data)).substring(0, 20);
        String location = folder + key;
        s3Client.upload(data, location);
        Resource resource = resourceRepository.save(mapper.toEntity(location));
        MetaDataDto metaDataDto = metaDataService.getMetaData(data);
        metaDataDto.setResourceId(resource.getId());
        songClient.create(metaDataDto);
        return mapper.toIdDto(resource);
    }

    @Override
    public byte[] download(Long id) {
        String location = resourceRepository.findById(id)
                .orElseThrow(NoSuchElementException::new)
                .getLocation();
        try {
            return s3Client.download(location);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResourceIdsDto delete(List<Long> ids) {
        resourceRepository.deleteAllById(ids);
        songClient.deleteByIds(ids);
        for (Long id : ids) {
            String location = resourceRepository.findById(id)
                    .orElseThrow(NoSuchElementException::new)
                    .getLocation();
            s3Client.delete(location);
        }
        return mapper.toDto(ids);
    }
}
