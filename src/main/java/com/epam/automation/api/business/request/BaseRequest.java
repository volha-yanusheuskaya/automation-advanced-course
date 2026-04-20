package com.epam.automation.api.business.request;

import com.epam.automation.common.core.config.ConfigurationReader;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.remote.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class BaseRequest {

    private HttpMethod method;
    private String url;

    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();
    private Map<String, String> pathParams = new HashMap<>();

    private Object body;

    public ValidatableResponse execute(RequestSpecification spec) {
        if (getBody() != null) {
            spec.body(getBody());
        }

        return spec
                .headers(getHeaders())
                .pathParams(getPathParams())
                .queryParams(getQueryParams())
                .when()
                .request(getMethod().name(),
                        String.format("/%s/%s", ConfigurationReader.getDemoProjectName(), getUrl()))
                .then().log().all();
    }
}
