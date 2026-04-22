package com.epam.automation.api.business.model.dto;

import lombok.Data;

@Data
public class PostItemRequestDto {
    private String name;
    private String startTime;
    private String endTime;
    private String type;
    private String status;
    private String launchUuid;
}
