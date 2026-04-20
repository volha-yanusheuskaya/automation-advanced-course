package com.epam.automation.api.business.model.mapper;

import com.epam.automation.api.business.model.domain.AnalyzeLaunch;
import com.epam.automation.api.business.model.dto.PostLaunchAnalyzeRequestDto;
import org.modelmapper.ModelMapper;

public class AnalyzeLaunchesMapper {

    static ModelMapper mapper = new ModelMapper();

    static {
        mapper.typeMap(AnalyzeLaunch.class, PostLaunchAnalyzeRequestDto.class);
    }

    public static <T> T map(AnalyzeLaunch launch, Class<T> targetClass) {
        return mapper.map(launch, targetClass);
    }
}
