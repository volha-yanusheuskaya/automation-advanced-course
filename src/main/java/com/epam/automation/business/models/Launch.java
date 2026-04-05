package com.epam.automation.business.models;

public class Launch {
    private String name;
    private String date;
    private int totalSteps;
    private int passedSteps;
    private int failedSteps;
    private int skippedSteps;
    private int productBugCount;
    private int autoBugCount;
    private int systemIssueCount;
    private int toInvestigateCount;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public int getPassedSteps() {
        return passedSteps;
    }

    public void setPassedSteps(int passedSteps) {
        this.passedSteps = passedSteps;
    }

    public int getFailedSteps() {
        return failedSteps;
    }

    public void setFailedSteps(int failedSteps) {
        this.failedSteps = failedSteps;
    }

    public int getSkippedSteps() {
        return skippedSteps;
    }

    public void setSkippedSteps(int skippedSteps) {
        this.skippedSteps = skippedSteps;
    }

    public int getProductBugCount() {
        return productBugCount;
    }

    public void setProductBugCount(int productBugCount) {
        this.productBugCount = productBugCount;
    }

    public int getAutoBugCount() {
        return autoBugCount;
    }

    public void setAutoBugCount(int autoBugCount) {
        this.autoBugCount = autoBugCount;
    }

    public int getSystemIssueCount() {
        return systemIssueCount;
    }

    public void setSystemIssueCount(int systemIssueCount) {
        this.systemIssueCount = systemIssueCount;
    }

    public int getToInvestigateCount() {
        return toInvestigateCount;
    }

    public void setToInvestigateCount(int toInvestigateCount) {
        this.toInvestigateCount = toInvestigateCount;
    }

    @Override
    public String toString() {
        return "Launch{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", totalTests=" + totalSteps +
                ", passedTests=" + passedSteps +
                ", failedTests=" + failedSteps +
                ", skippedTests=" + skippedSteps +
                '}';
    }
}
