package com.epam.automation.tests.api;

import com.epam.automation.api.business.client.ApiClient;
import com.epam.automation.api.business.model.domain.Item;
import com.epam.automation.api.business.model.domain.Launch;
import com.epam.automation.api.business.model.dto.PostItemRequestDto;
import com.epam.automation.api.business.model.dto.PostLaunchRequestDto;
import com.epam.automation.api.business.model.managers.ItemManager;
import com.epam.automation.api.business.model.managers.LaunchManager;
import com.epam.automation.api.business.model.mapper.ItemsMapper;
import com.epam.automation.api.business.model.mapper.LaunchesMapper;
import com.epam.automation.common.core.model.StatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;

@Tag("api")
public abstract class BaseApiTest {

    protected static final int OK = StatusCode.OK.getCode();
    protected static final int CREATED = StatusCode.CREATED.getCode();
    protected static final int BAD_REQUEST = StatusCode.BAD_REQUEST.getCode();
    protected static final int NOT_FOUND = StatusCode.NOT_FOUND.getCode();
    protected static final int NOT_ACCEPTABLE = StatusCode.NOT_ACCEPTABLE.getCode();

    protected final ApiClient api = new ApiClient();

    protected Integer launchId;

    @AfterEach
    void cleanup() {
        deleteLaunch(launchId);
    }

    protected String startLaunch(String launchName, String launchStartTime) {
        Launch launch = LaunchManager.getLaunchByNameAndStartTime(launchName, launchStartTime);
        PostLaunchRequestDto dto = LaunchesMapper.map(launch, PostLaunchRequestDto.class);

        return api.launches.create(dto)
                .statusCode(CREATED)
                .extract().path("id");
    }

    protected void addFinishedItem(String itemName, String itemStartTime, String itemEndTime, String launchUuid) {
        Item item = ItemManager.getItemByNameAndTime(itemName, itemStartTime, itemEndTime, launchUuid);
        PostItemRequestDto dto = ItemsMapper.map(item, PostItemRequestDto.class);

        String itemId = api.items.start(dto)
                .statusCode(CREATED)
                .extract().path("id");

        api.items.finish(itemId, dto)
                .statusCode(OK);
    }

    protected void finishLaunch(String launchEndTime, String launchUuid) {
        Launch launch = LaunchManager.getLaunchByEndTime(launchEndTime);
        PostLaunchRequestDto dto = LaunchesMapper.map(launch, PostLaunchRequestDto.class);

        api.launches.finish(launchUuid, dto)
                .statusCode(OK);
    }

    protected int resolveLaunchId(String launchUuid) {
        return api.launches.getList()
                .statusCode(OK)
                .extract().path("content.find { it.uuid == '%s' }.id", launchUuid);
    }

    protected void deleteLaunch(Integer launchId) {
        if (launchId == null) return;
        try {
            api.launches.delete(launchId);
        } catch (Exception ignored) {

        }

    }
}
