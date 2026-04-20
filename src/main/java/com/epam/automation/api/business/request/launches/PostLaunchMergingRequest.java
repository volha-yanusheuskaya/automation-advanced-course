package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.model.dto.PostLaunchMergeRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static org.openqa.selenium.remote.http.HttpMethod.POST;

public class PostLaunchMergingRequest extends BaseRequest {

    public PostLaunchMergingRequest(PostLaunchMergeRequestDto dto) {
        setMethod(POST);
        setUrl("launch/merge");
        setBody(dto);
    }
}
