package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.request.BaseRequest;

import static org.openqa.selenium.remote.http.HttpMethod.DELETE;

public class DeleteLaunchesRequest extends BaseRequest {

    public DeleteLaunchesRequest(int launchId) {
        setMethod(DELETE);
        setUrl(String.format("launch/%d", launchId));
    }
}
