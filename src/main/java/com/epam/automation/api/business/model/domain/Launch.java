package com.epam.automation.api.business.model.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Launch {
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private String mode;
    private boolean rerun;
}
