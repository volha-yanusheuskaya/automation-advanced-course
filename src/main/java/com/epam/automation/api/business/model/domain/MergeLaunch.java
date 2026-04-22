package com.epam.automation.api.business.model.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MergeLaunch {
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private String mode;
    private String mergeType;
    private Boolean extendSuitesDescription;
    private List<Integer> launches;
}
