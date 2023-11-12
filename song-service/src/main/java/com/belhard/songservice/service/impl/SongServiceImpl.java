package com.belhard.songservice.service.impl;

import com.belhard.songservice.service.SongService;
import com.belhard.songservice.service.dto.MetaDataDto;
import com.belhard.songservice.service.dto.ResourceIdDto;
import com.belhard.songservice.service.dto.ResourceIdsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Override
    public MetaDataDto getById(Long id) {
        return null;
    }

    @Override
    public ResourceIdDto save(MetaDataDto metaDataDto) {
        return null;
    }

    @Override
    public ResourceIdsDto deleteAllById(List<Long> idsToDelete) {
        return null;
    }
}
