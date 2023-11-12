package com.belhard.songservice.web;

import com.belhard.songservice.service.SongService;
import com.belhard.songservice.service.dto.MetaDataDto;
import com.belhard.songservice.service.dto.ResourceIdDto;
import com.belhard.songservice.service.dto.ResourceIdsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public ResourceIdDto saveMetaData(MetaDataDto metaDataDto) {
        return songService.save(metaDataDto);
    }

    @GetMapping("/{id}")
    public MetaDataDto getById(@PathVariable long id) {
        return songService.getById(id);
    }

    @DeleteMapping
    public ResourceIdsDto deleteAllById(@RequestParam List<Long> ids){
        return songService.deleteAllById(ids);
    }
}
