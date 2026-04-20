package com.epam.automation.api.business.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostLaunchMergeRequestDto {
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private String mode;
    private Boolean extendSuitesDescription;
    private List<Integer> launches;

    @JsonProperty("mergeType")
    private String mergeStrategyType;
}
