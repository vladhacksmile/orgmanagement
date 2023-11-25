package com.vladhacksmile.orgmanagement.model.result;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Status {
    OK(1, HttpStatus.OK),
    CREATED(2, HttpStatus.CREATED),
    NOT_FOUND(2, HttpStatus.NOT_FOUND),
    INTERNAL_ERROR(3, HttpStatus.INTERNAL_SERVER_ERROR),
    INCORRECT_PARAMS(4, HttpStatus.BAD_REQUEST);

    private final int id;
    private final HttpStatus httpStatus;

    Status(int id, HttpStatus httpStatus) {
        this.id = id;
        this.httpStatus = httpStatus;
    }
}