package com.vladhacksmile.orgmanagement.model.result;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Status {
    OK(1, 200),
    CREATED(2, 201),
    NOT_FOUND(2, 404),
    INTERNAL_ERROR(3, 503),
    INCORRECT_PARAMS(4, 400);

    private final int id;
    private final int httpStatus;

    Status(int id, int httpStatus) {
        this.id = id;
        this.httpStatus = httpStatus;
    }
}