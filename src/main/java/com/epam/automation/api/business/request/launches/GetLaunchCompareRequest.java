package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.request.BaseRequest;

import static org.openqa.selenium.remote.http.HttpMethod.GET;

public class GetLaunchCompareRequest extends BaseRequest {

    public GetLaunchCompareRequest(int firstLaunchId, int secondLaunchId) {
        setMethod(GET);
        setUrl("launch/compare");
        getQueryParams().put("ids", firstLaunchId + "," + secondLaunchId);
    }

}
