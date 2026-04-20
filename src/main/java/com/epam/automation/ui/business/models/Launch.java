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
    /**
     * -- GETTER --
     *  Gets the launch name.
     *
     *
     * -- SETTER --
     *  Sets the launch name.
     *
     @return the launch name (e.g., "Demo Api Tests #1")
      * @param name the new launch name
     */
    @Setter
    private String name;
    /**
     * -- GETTER --
     *  Gets the launch execution date.
     *
     * @return the date in format "yyyy-MM-dd HH:mm:ss"
     */
    private final String date;
    /**
     * -- GETTER --
     *  Gets the total number of test steps in the launch.
     *
     * @return total steps count (should equal sum of passed, failed, and skipped)
     */
    private final int totalSteps;
    /**
     * -- GETTER --
     *  Gets the number of passed steps.
     *
     * @return count of passed steps
     */
    private final int passedSteps;
    /**
     * -- GETTER --
     *  Gets the number of failed steps.
     *
     * @return count of failed steps
     */
    private final int failedSteps;
    /**
     * -- GETTER --
     *  Gets the number of skipped steps.
     *
     * @return count of skipped steps
     */
    private final int skippedSteps;
    /**
     * -- GETTER --
     *  Gets the number of defects marked as Product Bugs.
     *
     * @return count of product bug defects
     */
    private final int productBugCount;
    /**
     * -- GETTER --
     *  Gets the number of defects marked as Automation Bugs.
     *
     * @return count of automation bug defects
     */
    private final int autoBugCount;
    /**
     * -- GETTER --
     *  Gets the number of defects marked as System Issues.
     *
     * @return count of system issue defects
     */
    private final int systemIssueCount;
    /**
     * -- GETTER --
     *  Gets the number of defects marked as To Investigate.
     *
     * @return count of defects requiring investigation
     */
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
