package com.epam.automation.api.business.model.managers;

import com.epam.automation.api.business.model.domain.MergeLaunch;

import java.util.List;

public class MergeLaunchManager {

    public static MergeLaunch getMergeLaunch(
            String name, String startTime, String endTime,
            int firstLaunchId, int secondLaunchId) {

        return MergeLaunch.builder()
                .name(name)
                .description("### **Merged launch.**")
                .startTime(startTime)
                .endTime(endTime)
                .mode("DEFAULT")
                .mergeStrategyType("BASIC")
                .extendSuitesDescription(true)
                .launches(List.of(firstLaunchId, secondLaunchId))
                .build();
    }
}
