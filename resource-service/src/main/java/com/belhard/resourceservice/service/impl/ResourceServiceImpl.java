package com.belhard.resourceservice.service.impl;

import com.belhard.resourceservice.data.ResourceRepository;
import com.belhard.resourceservice.data.entities.Resource;
import com.belhard.resourceservice.service.MetaDataService;
import com.belhard.resourceservice.service.ResourceMapper;
import com.belhard.resourceservice.service.ResourceService;
import com.belhard.resourceservice.service.dto.MetaDataDto;
import com.belhard.resourceservice.service.dto.ResourceIdDto;
import com.belhard.resourceservice.service.dto.ResourceIdsDto;
//import com.belhard.songservice.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;

    private final MetaDataService metaDataService;

//    private final SongService songService;

    @Override
    public ResourceIdDto upload(byte[] data) {
        Resource resource = resourceRepository.save(mapper.toEntity(data));
        MetaDataDto metaDataDto = metaDataService.getMetaData(data);
        metaDataDto.setResourceId(resource.getId());
//        songService.save(metaDataDto);
        return mapper.toIdDto(resource);
    }

    @Override
    public byte[] download(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow()
                .getAudio();
    }

    @Override
    public ResourceIdsDto delete(List<Long> ids) {
        resourceRepository.deleteAllById(ids);
        return mapper.toDto(ids);
    }
}
