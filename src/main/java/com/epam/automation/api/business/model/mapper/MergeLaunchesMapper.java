package com.epam.automation.api.business.model.mapper;

import com.epam.automation.api.business.model.domain.MergeLaunch;
import com.epam.automation.api.business.model.dto.PostLaunchMergeRequestDto;
import org.modelmapper.ModelMapper;

public class MergeLaunchesMapper {
    private static final ModelMapper MAPPER = new ModelMapper();

    private MergeLaunchesMapper() {
    }

    public static PostLaunchMergeRequestDto map(MergeLaunch launch) {
        return MAPPER.map(launch, PostLaunchMergeRequestDto.class);
    }
}
