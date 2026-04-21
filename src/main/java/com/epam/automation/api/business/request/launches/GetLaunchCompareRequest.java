package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.request.BaseRequest;

import static com.epam.automation.api.business.request.HttpMethod.GET;
import static com.epam.automation.api.business.request.ProjectPath.demoProjectPath;

public class GetLaunchCompareRequest extends BaseRequest {

    public GetLaunchCompareRequest(int firstLaunchId, int secondLaunchId) {
        setMethod(GET);
        setUrl(demoProjectPath("launch/compare"));
        getQueryParams().put("ids", firstLaunchId + "," + secondLaunchId);
    }

}
