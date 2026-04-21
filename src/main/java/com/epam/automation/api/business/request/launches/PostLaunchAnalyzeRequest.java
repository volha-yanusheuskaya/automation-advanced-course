package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.model.dto.PostLaunchAnalyzeRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static com.epam.automation.api.business.request.HttpMethod.POST;
import static com.epam.automation.api.business.request.ProjectPath.demoProjectPath;

public class PostLaunchAnalyzeRequest extends BaseRequest {

    public PostLaunchAnalyzeRequest(PostLaunchAnalyzeRequestDto dto) {
        setMethod(POST);
        setUrl(demoProjectPath("launch/analyze"));
        setBody(dto);
    }
}
