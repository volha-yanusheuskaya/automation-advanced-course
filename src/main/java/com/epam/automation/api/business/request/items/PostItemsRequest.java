package com.epam.automation.api.business.request.items;

import com.epam.automation.api.business.model.dto.PostItemRequestDto;
import com.epam.automation.api.business.request.BaseRequest;

import static com.epam.automation.api.business.request.HttpMethod.POST;
import static com.epam.automation.api.business.request.ProjectPath.demoProjectPath;

public class PostItemsRequest extends BaseRequest {

    public PostItemsRequest(PostItemRequestDto dto) {
        setMethod(POST);
        setUrl(demoProjectPath("item"));
        setBody(dto);
    }
}
