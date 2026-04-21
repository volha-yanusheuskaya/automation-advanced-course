package com.epam.automation.api.business.model.mapper;

import com.epam.automation.api.business.model.domain.AnalyzeLaunch;
import com.epam.automation.api.business.model.dto.PostLaunchAnalyzeRequestDto;
import org.modelmapper.ModelMapper;

public class AnalyzeLaunchesMapper {
    private static final ModelMapper MAPPER = new ModelMapper();

    private AnalyzeLaunchesMapper() {
    }

    public static PostLaunchAnalyzeRequestDto map(AnalyzeLaunch launch) {
        return MAPPER.map(launch, PostLaunchAnalyzeRequestDto.class);
    }
}
