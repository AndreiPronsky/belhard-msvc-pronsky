package com.belhard.songservice.service;

import com.belhard.songservice.service.dto.MetaDataDto;
import com.belhard.songservice.service.dto.ResourceIdDto;
import com.belhard.songservice.service.dto.ResourceIdsDto;

import java.util.List;

public interface SongService {

    MetaDataDto getById(Long id);

    ResourceIdDto save(MetaDataDto metaDataDto);

    ResourceIdsDto deleteAllById(List<Long> idsToDelete);
}
