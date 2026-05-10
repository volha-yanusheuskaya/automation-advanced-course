package com.epam.automation.api.business.model.managers;

import com.epam.automation.api.business.model.domain.Item;

public class ItemManager {

    private ItemManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Item getItemByNameAndTime(String itemName, String startTime, String endTime, String launchUuid) {
        return Item.builder()
                .name(itemName)
                .startTime(startTime)
                .type("TEST")
                .endTime(endTime)
                .status("PASSED")
                .launchUuid(launchUuid)
                .build();
    }
}
