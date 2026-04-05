package com.epam.automation.business.models;

/**
 * Data model representing a test launch in Report Portal.
 * <p>
 * Contains information about a test launch including name, execution date,
 * and test statistics (passed, failed, skipped, defect counts).
 */
public class Launch {
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

    /**
     * Gets the launch name.
     *
     * @return the launch name (e.g., "Demo Api Tests #1")
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the launch name.
     *
     * @param name the new launch name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the launch execution date.
     *
     * @return the date in format "yyyy-MM-dd HH:mm:ss"
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the total number of test steps in the launch.
     *
     * @return total steps count (should equal sum of passed, failed, and skipped)
     */
    public int getTotalSteps() {
        return totalSteps;
    }

    /**
     * Gets the number of passed steps.
     *
     * @return count of passed steps
     */
    public int getPassedSteps() {
        return passedSteps;
    }

    /**
     * Gets the number of failed steps.
     *
     * @return count of failed steps
     */
    public int getFailedSteps() {
        return failedSteps;
    }

    /**
     * Gets the number of skipped steps.
     *
     * @return count of skipped steps
     */
    public int getSkippedSteps() {
        return skippedSteps;
    }

    /**
     * Gets the number of defects marked as Product Bugs.
     *
     * @return count of product bug defects
     */
    public int getProductBugCount() {
        return productBugCount;
    }

    /**
     * Gets the number of defects marked as Automation Bugs.
     *
     * @return count of automation bug defects
     */
    public int getAutoBugCount() {
        return autoBugCount;
    }

    /**
     * Gets the number of defects marked as System Issues.
     *
     * @return count of system issue defects
     */
    public int getSystemIssueCount() {
        return systemIssueCount;
    }

    /**
     * Gets the number of defects marked as To Investigate.
     *
     * @return count of defects requiring investigation
     */
    public int getToInvestigateCount() {
        return toInvestigateCount;
    }
}
