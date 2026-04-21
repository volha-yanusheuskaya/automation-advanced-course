package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.model.dto.PostLaunchMergeRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static com.epam.automation.api.business.request.HttpMethod.POST;
import static com.epam.automation.api.business.request.ProjectPath.demoProjectPath;

public class PostLaunchMergingRequest extends BaseRequest {

    public PostLaunchMergingRequest(PostLaunchMergeRequestDto dto) {
        setMethod(POST);
        setUrl(demoProjectPath("launch/merge"));
        setBody(dto);
    }
}
