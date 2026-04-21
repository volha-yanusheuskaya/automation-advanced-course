package com.epam.automation.tests.api.launches.request;

import com.epam.automation.api.business.request.BaseRequest;

import static com.epam.automation.api.business.request.HttpMethod.GET;
import static com.epam.automation.api.business.request.ProjectPath.demoProjectPath;

public class GetLaunchesListWrongPathRequest extends BaseRequest {

    public GetLaunchesListWrongPathRequest() {
        setMethod(GET);
        setUrl(demoProjectPath("launches"));
    }
}
