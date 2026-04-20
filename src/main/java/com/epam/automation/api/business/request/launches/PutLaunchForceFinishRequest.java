package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.model.dto.PostLaunchRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static org.openqa.selenium.remote.http.HttpMethod.PUT;

public class PutLaunchForceFinishRequest extends BaseRequest {

    public PutLaunchForceFinishRequest(int launchId, PostLaunchRequestDto dto) {
        setMethod(PUT);
        setUrl(String.format("launch/%d/stop", launchId));
        setBody(dto);
    }
}
