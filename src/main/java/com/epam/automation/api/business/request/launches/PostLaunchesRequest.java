package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.model.dto.PostLaunchRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static com.epam.automation.api.business.request.HttpMethod.POST;
import static com.epam.automation.api.business.request.ProjectPath.demoProjectPath;

public class PostLaunchesRequest extends BaseRequest {

    public PostLaunchesRequest(PostLaunchRequestDto dto) {
        setMethod(POST);
        setUrl(demoProjectPath("launch"));
        setBody(dto);
    }
}
