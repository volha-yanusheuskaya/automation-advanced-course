package com.epam.automation.tests.unit.common.core.model;

import com.epam.automation.common.core.model.StatusCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StatusCode Unit Tests")
class StatusCodeTest {

    @Test
    @DisplayName("Should have OK status code with value 200")
    void okStatusCodeHasValue200() {
        assertThat(StatusCode.OK.getCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("Should have CREATED status code with value 201")
    void createdStatusCodeHasValue201() {
        assertThat(StatusCode.CREATED.getCode()).isEqualTo(201);
    }

    @Test
    @DisplayName("Should have ACCEPTED status code with value 202")
    void acceptedStatusCodeHasValue202() {
        assertThat(StatusCode.ACCEPTED.getCode()).isEqualTo(202);
    }

    @Test
    @DisplayName("Should have NO_CONTENT status code with value 204")
    void noContentStatusCodeHasValue204() {
        assertThat(StatusCode.NO_CONTENT.getCode()).isEqualTo(204);
    }

    @Test
    @DisplayName("Should have BAD_REQUEST status code with value 400")
    void badRequestStatusCodeHasValue400() {
        assertThat(StatusCode.BAD_REQUEST.getCode()).isEqualTo(400);
    }

    @Test
    @DisplayName("Should have UNAUTHORIZED status code with value 401")
    void unauthorizedStatusCodeHasValue401() {
        assertThat(StatusCode.UNAUTHORIZED.getCode()).isEqualTo(401);
    }

    @Test
    @DisplayName("Should have FORBIDDEN status code with value 403")
    void forbiddenStatusCodeHasValue403() {
        assertThat(StatusCode.FORBIDDEN.getCode()).isEqualTo(403);
    }

    @Test
    @DisplayName("Should have NOT_FOUND status code with value 404")
    void notFoundStatusCodeHasValue404() {
        assertThat(StatusCode.NOT_FOUND.getCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("Should have METHOD_NOT_ALLOWED status code with value 405")
    void methodNotAllowedStatusCodeHasValue405() {
        assertThat(StatusCode.METHOD_NOT_ALLOWED.getCode()).isEqualTo(405);
    }

    @Test
    @DisplayName("Should have NOT_ACCEPTABLE status code with value 406")
    void notAcceptableStatusCodeHasValue406() {
        assertThat(StatusCode.NOT_ACCEPTABLE.getCode()).isEqualTo(406);
    }

    @Test
    @DisplayName("Should have INTERNAL_SERVER_ERROR status code with value 500")
    void internalServerErrorStatusCodeHasValue500() {
        assertThat(StatusCode.INTERNAL_SERVER_ERROR.getCode()).isEqualTo(500);
    }

    @Test
    @DisplayName("Should contain all 11 status codes")
    void allStatusCodesArePresent() {
        StatusCode[] values = StatusCode.values();
        assertThat(values).hasSize(11);
    }

    @Test
    @DisplayName("Should retrieve StatusCode by name using valueOf")
    void valueOfRetrievesStatusCodeByName() {
        assertThat(StatusCode.valueOf("OK")).isEqualTo(StatusCode.OK);
        assertThat(StatusCode.valueOf("CREATED")).isEqualTo(StatusCode.CREATED);
        assertThat(StatusCode.valueOf("NOT_FOUND")).isEqualTo(StatusCode.NOT_FOUND);
    }

    @Test
    @DisplayName("Should have correct HTTP status codes for 2xx responses")
    void twoHundredStatusCodesHaveCorrectValues() {
        assertThat(StatusCode.OK.getCode()).isBetween(200, 299);
        assertThat(StatusCode.CREATED.getCode()).isBetween(200, 299);
        assertThat(StatusCode.ACCEPTED.getCode()).isBetween(200, 299);
        assertThat(StatusCode.NO_CONTENT.getCode()).isBetween(200, 299);
    }

    @Test
    @DisplayName("Should have correct HTTP status codes for 4xx responses")
    void fourHundredStatusCodesHaveCorrectValues() {
        assertThat(StatusCode.BAD_REQUEST.getCode()).isBetween(400, 499);
        assertThat(StatusCode.UNAUTHORIZED.getCode()).isBetween(400, 499);
        assertThat(StatusCode.FORBIDDEN.getCode()).isBetween(400, 499);
        assertThat(StatusCode.NOT_FOUND.getCode()).isBetween(400, 499);
        assertThat(StatusCode.METHOD_NOT_ALLOWED.getCode()).isBetween(400, 499);
        assertThat(StatusCode.NOT_ACCEPTABLE.getCode()).isBetween(400, 499);
    }

    @Test
    @DisplayName("Should have correct HTTP status code for 5xx response")
    void fiveHundredStatusCodeHasCorrectValue() {
        assertThat(StatusCode.INTERNAL_SERVER_ERROR.getCode()).isBetween(500, 599);
    }

    @Test
    @DisplayName("Should be comparable using getCode method")
    void statusCodesAreComparable() {
        assertThat(StatusCode.OK.getCode())
                .as("OK should be less than BAD_REQUEST")
                .isLessThan(StatusCode.BAD_REQUEST.getCode());
        
        assertThat(StatusCode.INTERNAL_SERVER_ERROR.getCode())
                .as("INTERNAL_SERVER_ERROR should be greater than NOT_FOUND")
                .isGreaterThan(StatusCode.NOT_FOUND.getCode());
    }

    @Test
    @DisplayName("Should have non-zero HTTP status codes")
    void allStatusCodesAreNonZero() {
        for (StatusCode statusCode : StatusCode.values()) {
            assertThat(statusCode.getCode()).isPositive();
        }
    }
}
