package com.epam.automation.api.business.model.mapper;

import com.epam.automation.api.business.model.domain.Launch;
import com.epam.automation.api.business.model.dto.PostLaunchRequestDto;
import org.modelmapper.ModelMapper;

public class LaunchesMapper {
    private static final ModelMapper MAPPER = new ModelMapper();

    private LaunchesMapper() {
    }

    public static PostLaunchRequestDto map(Launch launch) {
        return MAPPER.map(launch, PostLaunchRequestDto.class);
    }

}
