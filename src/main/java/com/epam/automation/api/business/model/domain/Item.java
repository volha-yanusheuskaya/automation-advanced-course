package com.epam.automation.api.business.model.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private String name;
    private String startTime;
    private String endTime;
    private String type;
    private String status;
    private String launchUuid;
}
