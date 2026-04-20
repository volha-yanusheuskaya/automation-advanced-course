package com.epam.automation.api.business.request.launches;

import com.epam.automation.api.business.request.BaseRequest;

import static org.openqa.selenium.remote.http.HttpMethod.GET;

public class GetLaunchesListWrongPathRequest extends BaseRequest {

    public GetLaunchesListWrongPathRequest() {
        setMethod(GET);
        setUrl("launches");
    }
}
