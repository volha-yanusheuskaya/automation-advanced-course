package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.request.BaseRequest;

import static com.epam.automation.api.business.request.HttpMethod.GET;
import static com.epam.automation.api.business.request.ProjectPath.demoProjectPath;

public class GetLaunchesListRequest extends BaseRequest {

    public GetLaunchesListRequest() {
        setMethod(GET);
        setUrl(demoProjectPath("launch"));
    }
}
