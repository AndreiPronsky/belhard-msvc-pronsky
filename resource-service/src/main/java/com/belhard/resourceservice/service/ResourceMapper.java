package com.belhard.resourceservice.service;

import com.belhard.resourceservice.data.entities.Resource;
import com.belhard.resourceservice.service.dto.ResourceIdDto;
import com.belhard.resourceservice.service.dto.ResourceIdsDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

    default Resource toEntity(String location) {
        Resource resource = new Resource();
        resource.setLocation(location);
        return resource;
    }

    default ResourceIdsDto toDto(List<Long> ids) {
        ResourceIdsDto resourceIdsDto = new ResourceIdsDto();
        resourceIdsDto.setIds(ids);
        return resourceIdsDto;
    }

    default ResourceIdDto toIdDto(Resource resource) {
        ResourceIdDto resourceIdDto = new ResourceIdDto();
        resourceIdDto.setId(resource.getId());
        return resourceIdDto;
    }
}
