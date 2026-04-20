package com.epam.automation.api.business.client;

import com.epam.automation.api.business.controller.ItemController;
import com.epam.automation.api.business.controller.LaunchesController;
import com.epam.automation.api.business.request.BaseRequest;
import com.epam.automation.common.core.config.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    public final LaunchesController launches;
    public final ItemController items;

    private final RequestSpecification baseSpec;

    public ApiClient() {
        this.baseSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigurationReader.getBaseUri())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", String.format("Bearer %s", ConfigurationReader.getToken()))
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        this.launches = new LaunchesController(this);
        this.items = new ItemController(this);
    }

    public ValidatableResponse execute(BaseRequest request) {
        return request.execute(RestAssured.given().spec(baseSpec));
    }

}
