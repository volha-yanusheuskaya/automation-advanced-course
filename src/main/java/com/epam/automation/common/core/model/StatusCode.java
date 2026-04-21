package com.epam.automation.common.core.model;

import lombok.Getter;

@Getter
public enum StatusCode {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

}
