package com.epam.automation.ui.business.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data model representing a test launch in Report Portal.
 * <p>
 * Contains information about a test launch including name, execution date,
 * and test statistics (passed, failed, skipped, defect counts).
 */
@Getter
@Builder
public class Launch {
    @Setter
    private String name;
    private final String date;
    private final int totalSteps;
    private final int passedSteps;
    private final int failedSteps;
    private final int skippedSteps;
    private final int productBugCount;
    private final int autoBugCount;
    private final int systemIssueCount;
    private final int toInvestigateCount;
}
