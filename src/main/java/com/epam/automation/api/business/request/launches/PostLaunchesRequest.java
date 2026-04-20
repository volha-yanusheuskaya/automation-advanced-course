package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.model.dto.PostLaunchRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static org.openqa.selenium.remote.http.HttpMethod.POST;

public class PostLaunchesRequest extends BaseRequest {

    public PostLaunchesRequest(PostLaunchRequestDto dto) {
        setMethod(POST);
        setUrl("launch");
        setBody(dto);
    }
}
