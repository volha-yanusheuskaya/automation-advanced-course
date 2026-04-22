package com.epam.automation.api.business.model.dto;

import lombok.Data;

@Data
public class PostLaunchRequestDto {
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private String mode;
    private boolean rerun;
}
