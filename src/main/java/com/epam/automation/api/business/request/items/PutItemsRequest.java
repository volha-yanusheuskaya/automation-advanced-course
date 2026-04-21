package com.epam.automation.api.business.request.items;

import com.epam.automation.api.business.model.dto.PostItemRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static com.epam.automation.api.business.request.HttpMethod.PUT;
import static com.epam.automation.api.business.request.ProjectPath.demoProjectPath;

public class PutItemsRequest extends BaseRequest {

    public PutItemsRequest(String itemId, PostItemRequestDto dto) {
        setMethod(PUT);
        setUrl(demoProjectPath(String.format("item/%s", itemId)));
        setBody(dto);
    }
}
