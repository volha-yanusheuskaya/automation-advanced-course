package com.epam.automation.tests.unit.api.business.client;

import com.epam.automation.api.business.client.ApiClient;
import com.epam.automation.api.business.controller.ItemController;
import com.epam.automation.api.business.controller.LaunchesController;
import com.epam.automation.api.business.request.BaseRequest;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ApiClient Unit Tests")
class ApiClientTest {

    @Test
    @DisplayName("Should initialize LaunchesController on construction")
    void shouldInitializeLaunchesController() {
        ApiClient client = new ApiClient();

        assertThat(client.launches)
                .isNotNull()
                .isInstanceOf(LaunchesController.class);
    }

    @Test
    @DisplayName("Should initialize ItemController on construction")
    void shouldInitializeItemController() {
        ApiClient client = new ApiClient();

        assertThat(client.items)
                .isNotNull()
                .isInstanceOf(ItemController.class);
    }

    @Test
    @DisplayName("Should delegate execute() to BaseRequest and return its ValidatableResponse")
    void shouldDelegateExecuteToRequestAndReturnResponse() {
        BaseRequest request = mock(BaseRequest.class);
        ValidatableResponse response = mock(ValidatableResponse.class);
        when(request.execute(any(RequestSpecification.class))).thenReturn(response);

        ApiClient client = new ApiClient();
        ValidatableResponse result = client.execute(request);

        assertThat(result).isInstanceOf(ValidatableResponse.class);
        verify(request, times(1)).execute(any(RequestSpecification.class));
    }

    @Test
    @DisplayName("Should create multiple independent ApiClient instances")
    void shouldCreateIndependentInstances() {
        ApiClient client1 = new ApiClient();
        ApiClient client2 = new ApiClient();

        assertThat(client1).isNotSameAs(client2);
        assertThat(client1.launches).isNotSameAs(client2.launches);
        assertThat(client1.items).isNotSameAs(client2.items);
    }
}
