package com.belhard.resourceservice.service.impl;

import com.belhard.resourceservice.client.SongClient;
import com.belhard.resourceservice.data.ResourceRepository;
import com.belhard.resourceservice.data.entities.Resource;
import com.belhard.resourceservice.service.MetaDataService;
import com.belhard.resourceservice.service.ResourceMapper;
import com.belhard.resourceservice.service.ResourceService;
import com.belhard.resourceservice.service.dto.MetaDataDto;
import com.belhard.resourceservice.service.dto.ResourceIdDto;
import com.belhard.resourceservice.service.dto.ResourceIdsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;
    private final MetaDataService metaDataService;
    private final SongClient songClient;

    @Override
    public ResourceIdDto upload(byte[] data) {
        Resource resource = resourceRepository.save(mapper.toEntity(data));
        MetaDataDto metaDataDto = metaDataService.getMetaData(data);
        metaDataDto.setResourceId(resource.getId());
        songClient.create(metaDataDto);
        return mapper.toIdDto(resource);
    }

    @Override
    public byte[] download(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(NoSuchElementException::new)
                .getAudio();
    }

    @Override
    public ResourceIdsDto delete(List<Long> ids) {
        resourceRepository.deleteAllById(ids);
        songClient.deleteByIds(ids);
        return mapper.toDto(ids);
    }
}
