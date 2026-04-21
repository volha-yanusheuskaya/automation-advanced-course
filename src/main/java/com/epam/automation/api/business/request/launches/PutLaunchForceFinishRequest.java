package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.model.dto.PostLaunchRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static com.epam.automation.api.business.request.HttpMethod.PUT;
import static com.epam.automation.api.business.request.ProjectPath.demoProjectPath;

public class PutLaunchForceFinishRequest extends BaseRequest {

    public PutLaunchForceFinishRequest(int launchId, PostLaunchRequestDto dto) {
        setMethod(PUT);
        setUrl(demoProjectPath(String.format("launch/%d/stop", launchId)));
        setBody(dto);
    }
}
