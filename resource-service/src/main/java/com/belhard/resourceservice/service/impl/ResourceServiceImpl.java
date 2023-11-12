package com.belhard.resourceservice.service.impl;

import com.belhard.resourceservice.service.ResourceService;
import com.belhard.resourceservice.service.dto.ResourceIdDto;
import com.belhard.resourceservice.service.dto.ResourceIdsDto;

import java.util.List;

public class ResourceServiceImpl implements ResourceService {

    @Override
    public ResourceIdDto upload(byte[] data) {
        return null;
    }

    @Override
    public byte[] download(Long id) {
        return new byte[0];
    }

    @Override
    public ResourceIdsDto delete(List<Long> ids) {
        return null;
    }
}
