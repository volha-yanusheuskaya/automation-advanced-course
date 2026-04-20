package com.epam.automation.api.business.request.items;

import com.epam.automation.api.business.model.dto.PostItemRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static org.openqa.selenium.remote.http.HttpMethod.PUT;

public class PutItemsRequest extends BaseRequest {

    public PutItemsRequest(String itemId, PostItemRequestDto dto) {
        setMethod(PUT);
        setUrl(String.format("item/%s", itemId));
        setBody(dto);
    }
}
