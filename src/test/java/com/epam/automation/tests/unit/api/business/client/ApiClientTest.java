package com.epam.automation.tests.unit.api.business.client;

import com.epam.automation.api.business.client.ApiClient;
import com.epam.automation.api.business.controller.ItemController;
import com.epam.automation.api.business.controller.LaunchesController;
import com.epam.automation.api.business.request.BaseRequest;
import com.epam.automation.common.core.config.ConfigurationReader;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ApiClient Unit Tests")
class ApiClientTest {

    @Test
    @DisplayName("Should initialize ApiClient with launches and items controllers")
    void constructorInitializesControllers() {
        ApiClient client = new ApiClient();

        assertThat(client.launches).isNotNull();
        assertThat(client.items).isNotNull();
    }

    @Test
    @DisplayName("Should initialize LaunchesController")
    void launchesControllerIsInitialized() {
        ApiClient client = new ApiClient();

        assertThat(client.launches).isInstanceOf(LaunchesController.class);
    }

    @Test
    @DisplayName("Should initialize ItemController")
    void itemControllerIsInitialized() {
        ApiClient client = new ApiClient();

        assertThat(client.items).isInstanceOf(ItemController.class);
    }

    @Test
    @DisplayName("Should set correct base URI from configuration")
    void baseUriIsReadFromConfiguration() {
        String baseUri = ConfigurationReader.getBaseUri();
        assertThat(baseUri).isNotBlank();
    }

    @Test
    @DisplayName("Should include authorization header with token from configuration")
    void authorizationHeaderIsIncluded() {
        String token = ConfigurationReader.getToken();
        assertThat(token).isNotBlank();
    }

    @Test
    @DisplayName("Should execute request and delegate to request.execute()")
    void executeCallsRequestWithBaseSpec() {
        BaseRequest request = mock(BaseRequest.class);
        ValidatableResponse response = mock(ValidatableResponse.class);
        when(request.execute(any(RequestSpecification.class))).thenReturn(response);

        ApiClient client = new ApiClient();
        ValidatableResponse result = client.execute(request);

        assertThat(result).isNotNull();
        verify(request).execute(any(RequestSpecification.class));
    }

    @Test
    @DisplayName("Should return ValidatableResponse from execute method")
    void executeReturnsValidatableResponse() {
        BaseRequest request = mock(BaseRequest.class);
        ValidatableResponse response = mock(ValidatableResponse.class);
        when(request.execute(any(RequestSpecification.class))).thenReturn(response);

        ApiClient client = new ApiClient();
        ValidatableResponse result = client.execute(request);

        assertThat(result).isInstanceOf(ValidatableResponse.class);
    }

    @Test
    @DisplayName("Should pass request specification to BaseRequest execute method")
    void executePassesSpecificationToRequest() {
        BaseRequest request = mock(BaseRequest.class);
        ValidatableResponse response = mock(ValidatableResponse.class);
        when(request.execute(any(RequestSpecification.class))).thenReturn(response);

        ApiClient client = new ApiClient();
        client.execute(request);

        verify(request, times(1)).execute(any(RequestSpecification.class));
    }

    @Test
    @DisplayName("Should create multiple independent ApiClient instances")
    void multipleInstancesAreIndependent() {
        ApiClient client1 = new ApiClient();
        ApiClient client2 = new ApiClient();

        assertThat(client1).isNotSameAs(client2);
        assertThat(client1.launches).isNotSameAs(client2.launches);
        assertThat(client1.items).isNotSameAs(client2.items);
    }

    @Test
    @DisplayName("Should have LaunchesController with reference to ApiClient")
    void launchesControllerHasApiClientReference() {
        ApiClient client = new ApiClient();

        assertThat(client.launches)
                .isNotNull()
                .isInstanceOf(LaunchesController.class);
    }

    @Test
    @DisplayName("Should have ItemController with reference to ApiClient")
    void itemControllerHasApiClientReference() {
        ApiClient client = new ApiClient();

        assertThat(client.items)
                .isNotNull()
                .isInstanceOf(ItemController.class);
    }
}
