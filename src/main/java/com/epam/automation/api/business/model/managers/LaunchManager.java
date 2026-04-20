package com.epam.automation.api.business.model.managers;

import com.epam.automation.api.business.model.domain.Launch;

public class LaunchManager {

    public static Launch getLaunchByNameAndStartTime(String launchName, String startTime) {
        return Launch.builder()
                .name(launchName)
                .description("### **Demonstration launch.**")
                .startTime(startTime)
                .mode("DEFAULT")
                .rerun(false)
                .build();
    }

    public static Launch getLaunchByEndTime(String endTime) {
        return Launch.builder()
                .endTime(endTime)
                .build();
    }
}
