package com.belhard.songservice.web;

import com.belhard.songservice.service.dto.MetaDataDto;
import com.belhard.songservice.service.dto.ResourceIdDto;
import com.belhard.songservice.service.dto.ResourceIdsDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class SongController {

    @PostMapping
    public ResourceIdDto saveMetaData(MetaDataDto metaDataDto) {
        return null;
    }

    @GetMapping
    public MetaDataDto getById(@PathVariable long id) {
        return null;
    }

    @DeleteMapping
    public ResourceIdsDto deleteAllById(@RequestParam List<Long> ids){
        return null;
    }
}
