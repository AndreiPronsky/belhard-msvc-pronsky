package com.belhard.songservice.service;

import com.belhard.songservice.data.entities.MetaData;
import com.belhard.songservice.service.dto.MetaDataDto;
import com.belhard.songservice.service.dto.SongIdDto;
import com.belhard.songservice.service.dto.SongIdsDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SongMapper {

    MetaDataDto toDto(MetaData metaData);

    MetaData toEntity(MetaDataDto metaDataDto);

    default SongIdDto toDto(long id) {
        SongIdDto dto = new SongIdDto();
        dto.setId(id);
        return dto;
    }

    default SongIdsDto toDto(List<Long> ids) {
        SongIdsDto dto = new SongIdsDto();
        dto.setIds(ids);
        return dto;
    }
}
