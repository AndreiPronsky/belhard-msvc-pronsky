package com.belhard.songservice.service.impl;

import com.belhard.songservice.data.MetaDataRepository;
import com.belhard.songservice.service.SongMapper;
import com.belhard.songservice.service.SongService;
import com.belhard.songservice.service.dto.MetaDataDto;
import com.belhard.songservice.service.dto.SongIdDto;
import com.belhard.songservice.service.dto.SongIdsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final MetaDataRepository repository;
    private final SongMapper mapper;

    @Override
    public MetaDataDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("No Metadata with id: " + id));
    }

    @Override
    public SongIdDto save(MetaDataDto metaDataDto) {
        Long id = repository.save(mapper.toEntity(metaDataDto)).getId();
        return mapper.toDto(id);
    }

    @Override
    public SongIdsDto deleteAllById(List<Long> idsToDelete) {
        repository.deleteAllById(idsToDelete);
        return mapper.toDto(idsToDelete);
    }

}
