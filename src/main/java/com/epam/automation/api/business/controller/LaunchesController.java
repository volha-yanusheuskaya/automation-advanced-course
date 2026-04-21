package com.epam.automation.api.business.controller;

import com.epam.automation.api.business.client.ApiClient;
import com.epam.automation.api.business.model.dto.PostLaunchAnalyzeRequestDto;
import com.epam.automation.api.business.model.dto.PostLaunchMergeRequestDto;
import com.epam.automation.api.business.model.dto.PostLaunchRequestDto;
import com.epam.automation.api.business.request.launches.*;
import io.restassured.response.ValidatableResponse;


public class LaunchesController {

    private final ApiClient client;

    public LaunchesController(ApiClient apiClient) {
        this.client = apiClient;
    }

    public ValidatableResponse getList() {
        GetLaunchesListRequest request = new GetLaunchesListRequest();
        return this.client.execute(request);
    }

    public ValidatableResponse create(PostLaunchRequestDto dto) {
        PostLaunchesRequest request = new PostLaunchesRequest(dto);
        return this.client.execute(request);
    }

    public ValidatableResponse finish(String launchUuid, PostLaunchRequestDto dto) {
        PutLaunchesRequest request = new PutLaunchesRequest(launchUuid, dto);
        return this.client.execute(request);
    }

    public ValidatableResponse delete(int launchId) {
        DeleteLaunchesRequest request = new DeleteLaunchesRequest(launchId);
        return this.client.execute(request);
    }

    public ValidatableResponse analyze(PostLaunchAnalyzeRequestDto dto) {
        PostLaunchAnalyzeRequest request = new PostLaunchAnalyzeRequest(dto);
        return this.client.execute(request);
    }

    public ValidatableResponse compare(int firstLaunchId, int secondLaunchId) {
        GetLaunchCompareRequest request = new GetLaunchCompareRequest(firstLaunchId, secondLaunchId);
        return this.client.execute(request);
    }

    public ValidatableResponse stop(int launchId, PostLaunchRequestDto dto) {
        PutLaunchForceFinishRequest request = new PutLaunchForceFinishRequest(launchId, dto);
        return this.client.execute(request);
    }

    public ValidatableResponse merge(PostLaunchMergeRequestDto dto) {
        PostLaunchMergingRequest request = new PostLaunchMergingRequest(dto);
        return this.client.execute(request);
    }
}
