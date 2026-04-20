package com.epam.automation.api.business.model.mapper;

import com.epam.automation.api.business.model.domain.Launch;
import com.epam.automation.api.business.model.dto.PostLaunchRequestDto;
import org.modelmapper.ModelMapper;

public class LaunchesMapper {
    static ModelMapper mapper = new ModelMapper();

    static {
        mapper.typeMap(Launch.class, PostLaunchRequestDto.class);
    }

    public static <T> T map(Launch launch, Class<T> targetClass) {
        return mapper.map(launch, targetClass);
    }

}
