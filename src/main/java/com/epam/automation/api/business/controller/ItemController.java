package com.epam.automation.api.business.controller;

import com.epam.automation.api.business.client.ApiClient;
import com.epam.automation.api.business.model.dto.PostItemRequestDto;
import com.epam.automation.api.business.request.items.PostItemsRequest;
import com.epam.automation.api.business.request.items.PutItemsRequest;
import io.restassured.response.ValidatableResponse;

public class ItemController {

    private final ApiClient client;

    public ItemController(ApiClient apiClient) {
        this.client = apiClient;
    }

    public ValidatableResponse start(PostItemRequestDto dto) {
        PostItemsRequest request = new PostItemsRequest(dto);
        return this.client.execute(request);
    }

    public ValidatableResponse finish(String itemId, PostItemRequestDto dto) {
        PutItemsRequest request = new PutItemsRequest(itemId, dto);
        return this.client.execute(request);
    }

}
