package com.epam.automation.api.business.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostLaunchAnalyzeRequestDto {
    private Integer launchId;
    private String analyzerTypeName;
    private String analyzerMode;
    private List<String> analyzeItemsMode;
}
