package com.belhard.songservice.web;

import com.belhard.songservice.service.SongService;
import com.belhard.songservice.service.dto.MetaDataDto;
import com.belhard.songservice.service.dto.SongIdDto;
import com.belhard.songservice.service.dto.SongIdsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public SongIdDto saveMetaData(@RequestBody MetaDataDto metaDataDto) {
        return songService.save(metaDataDto);
    }

    @GetMapping("/{id}")
    public MetaDataDto getById(@PathVariable long id) {
        return songService.getById(id);
    }

    @DeleteMapping
    public SongIdsDto deleteAllById(@RequestParam List<Long> ids) {
        return songService.deleteAllById(ids);
    }
}
