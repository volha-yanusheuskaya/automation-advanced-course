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
import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import com.epam.automation.common.core.model.StatusCode;
import org.junit.jupiter.api.Tag;

import java.util.List;
import java.util.Map;

@Tag("api")
public abstract class BaseApiTest {

    private static final ILogger logger = LoggerFactory.getLogger(BaseApiTest.class);

    protected static final int OK = StatusCode.OK.getCode();
    protected static final int CREATED = StatusCode.CREATED.getCode();
    protected static final int BAD_REQUEST = StatusCode.BAD_REQUEST.getCode();
    protected static final int NOT_FOUND = StatusCode.NOT_FOUND.getCode();
    protected static final int NOT_ACCEPTABLE = StatusCode.NOT_ACCEPTABLE.getCode();

    protected final ApiClient api = new ApiClient();

    protected String startLaunch(String launchName, String launchStartTime) {
        Launch launch = LaunchManager.getLaunchByNameAndStartTime(launchName, launchStartTime);
        PostLaunchRequestDto dto = LaunchesMapper.map(launch);

        return api.launches.create(dto)
                .statusCode(CREATED)
                .extract().path("id");
    }

    protected void addFinishedItem(String itemName, String itemStartTime, String itemEndTime, String launchUuid) {
        Item item = ItemManager.getItemByNameAndTime(itemName, itemStartTime, itemEndTime, launchUuid);
        PostItemRequestDto dto = ItemsMapper.map(item);

        String itemId = api.items.start(dto)
                .statusCode(CREATED)
                .extract().path("id");

        api.items.finish(itemId, dto)
                .statusCode(OK);
    }

    protected void finishLaunch(String launchEndTime, String launchUuid) {
        Launch launch = LaunchManager.getLaunchByEndTime(launchEndTime);
        PostLaunchRequestDto dto = LaunchesMapper.map(launch);

        api.launches.finish(launchUuid, dto)
                .statusCode(OK);
    }

    protected int resolveLaunchId(String launchUuid) {
        List<Map<String, Object>> content = api.launches.getList()
                .statusCode(OK)
                .extract().jsonPath().getList("content");

        return content.stream()
                .filter(e -> launchUuid.equals(e.get("uuid")))
                .map(e -> (Integer) e.get("id"))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "Launch with uuid '" + launchUuid + "' not found"));
    }

    protected void deleteLaunch(Integer launchId) {
        if (launchId == null) return;
        try {
            api.launches.delete(launchId);
        } catch (Exception e) {
            logger.warn("Cleanup failed for launch id={} : {}", launchId, e);
        }
    }
}
