package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.model.dto.PostLaunchAnalyzeRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static org.openqa.selenium.remote.http.HttpMethod.POST;

public class PostLaunchAnalyzeRequest extends BaseRequest {

    public PostLaunchAnalyzeRequest(PostLaunchAnalyzeRequestDto dto) {
        setMethod(POST);
        setUrl("launch/analyze");
        setBody(dto);
    }
}
