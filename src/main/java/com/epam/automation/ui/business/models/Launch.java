package com.epam.automation.ui.business.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Data model representing a test launch in Report Portal.
 * <p>
 * Contains information about a test launch including name, execution date,
 * and test statistics (passed, failed, skipped, defect counts).
 */
@Getter
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

    /**
     * Constructs a Launch object with complete test statistics.
     *
     * @param name               the launch name (e.g., "Demo Api Tests #1")
     * @param date               the launch date in format "yyyy-MM-dd HH:mm:ss"
     * @param totalSteps         total number of test steps in the launch
     * @param passedSteps        number of passed steps
     * @param failedSteps        number of failed steps
     * @param skippedSteps       number of skipped steps
     * @param productBugCount    number of issues marked as Product Bugs
     * @param autoBugCount       number of issues marked as Automation Bugs
     * @param systemIssueCount   number of issues marked as System Issues
     * @param toInvestigateCount number of issues marked as To Investigate
     * @throws IllegalArgumentException if negative counts are provided
     */
    public Launch(String name, String date, int totalSteps, int passedSteps, int failedSteps,
                  int skippedSteps, int productBugCount, int autoBugCount,
                  int systemIssueCount, int toInvestigateCount) {
        this.name = name;
        this.date = date;
        this.totalSteps = totalSteps;
        this.passedSteps = passedSteps;
        this.failedSteps = failedSteps;
        this.skippedSteps = skippedSteps;
        this.productBugCount = productBugCount;
        this.autoBugCount = autoBugCount;
        this.systemIssueCount = systemIssueCount;
        this.toInvestigateCount = toInvestigateCount;
    }

}
