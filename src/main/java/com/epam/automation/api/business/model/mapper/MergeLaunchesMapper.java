package com.epam.automation.api.business.model.mapper;

import com.epam.automation.api.business.model.domain.MergeLaunch;
import com.epam.automation.api.business.model.dto.PostLaunchMergeRequestDto;
import org.modelmapper.ModelMapper;

public class MergeLaunchesMapper {

    static ModelMapper mapper = new ModelMapper();

    static {
        mapper.typeMap(MergeLaunch.class, PostLaunchMergeRequestDto.class);
    }

    public static <T> T map(MergeLaunch launch, Class<T> targetClass) {
        return mapper.map(launch, targetClass);
    }
}
