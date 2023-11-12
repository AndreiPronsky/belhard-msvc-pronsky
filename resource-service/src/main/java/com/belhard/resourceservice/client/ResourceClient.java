package com.belhard.resourceservice.client;

import com.belhard.resourceservice.service.dto.MetaDataDto;

import java.util.List;

public interface ResourceClient {

    void create(MetaDataDto metaDataDto);

    void deleteByIds(List<Long> resourceIds);
}
