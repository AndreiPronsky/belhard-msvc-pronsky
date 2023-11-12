package com.belhard.resourceservice.service;

import com.belhard.resourceservice.service.dto.ResourceIdDto;
import com.belhard.resourceservice.service.dto.ResourceIdsDto;

import java.util.List;

public interface ResourceService {

    ResourceIdDto upload(byte[] data);

    byte[] download(Long id);

    ResourceIdsDto delete(List<Long> ids);
}
