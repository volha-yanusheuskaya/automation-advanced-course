package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.model.dto.PostLaunchRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static org.openqa.selenium.remote.http.HttpMethod.PUT;

public class PutLaunchesRequest extends BaseRequest {

    public PutLaunchesRequest(String launchUuid, PostLaunchRequestDto dto) {
        setMethod(PUT);
        setUrl(String.format("launch/%s/finish", launchUuid));
        setBody(dto);
    }
}
