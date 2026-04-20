package com.epam.automation.api.business.request.items;

import com.epam.automation.api.business.model.dto.PostItemRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static org.openqa.selenium.remote.http.HttpMethod.POST;

public class PostItemsRequest extends BaseRequest {

    public PostItemsRequest(PostItemRequestDto dto) {
        setMethod(POST);
        setUrl("item");
        setBody(dto);
    }
}
