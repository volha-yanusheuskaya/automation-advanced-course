package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.request.BaseRequest;

import static com.epam.automation.api.business.request.HttpMethod.DELETE;
import static com.epam.automation.api.business.request.ProjectPath.demoProjectPath;

public class DeleteLaunchesRequest extends BaseRequest {

    public DeleteLaunchesRequest(int launchId) {
        setMethod(DELETE);
        setUrl(demoProjectPath(String.format("launch/%d", launchId)));
    }
}
