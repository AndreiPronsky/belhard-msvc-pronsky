package com.belhard.songservice.service;

import com.belhard.songservice.service.dto.MetaDataDto;
import com.belhard.songservice.service.dto.SongIdDto;
import com.belhard.songservice.service.dto.SongIdsDto;

import java.util.List;

public interface SongService {

    MetaDataDto getById(Long id);

    SongIdDto save(MetaDataDto metaDataDto);

    SongIdsDto deleteAllById(List<Long> idsToDelete);
}
