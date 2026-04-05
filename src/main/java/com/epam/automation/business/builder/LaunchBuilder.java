package com.epam.automation.business.builder;

import com.epam.automation.business.models.Launch;

public class LaunchBuilder {
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

    public LaunchBuilder name(String name) {
        this.name = name;
        return this;
    }

    public LaunchBuilder date(String date) {
        this.date = date;
        return this;
    }

    public LaunchBuilder totalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
        return this;
    }

    public LaunchBuilder passedSteps(int passedSteps) {
        this.passedSteps = passedSteps;
        return this;
    }

    public LaunchBuilder failedSteps(int failedSteps) {
        this.failedSteps = failedSteps;
        return this;
    }

    public LaunchBuilder skippedSteps(int skippedSteps) {
        this.skippedSteps = skippedSteps;
        return this;
    }

    public LaunchBuilder productBugCount(int productBugCount) {
        this.productBugCount = productBugCount;
        return this;
    }

    public LaunchBuilder autoBugCount(int autoBugCount) {
        this.autoBugCount = autoBugCount;
        return this;
    }

    public LaunchBuilder systemIssueCount(int systemIssueCount) {
        this.systemIssueCount = systemIssueCount;
        return this;
    }

    public LaunchBuilder toInvestigateCount(int toInvestigateCount) {
        this.toInvestigateCount = toInvestigateCount;
        return this;
    }

    public Launch build() {
        return new Launch(name, date, totalSteps, passedSteps, failedSteps, skippedSteps,
                productBugCount, autoBugCount, systemIssueCount, toInvestigateCount);
    }
}
