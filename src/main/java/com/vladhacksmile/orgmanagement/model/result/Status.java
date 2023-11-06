package com.vladhacksmile.orgmanagement.model.result;

import lombok.Getter;

@Getter
public enum Status {
    OK(1),
    NOT_FOUND(2),
    INTERNAL_ERROR(3);

    private final int id;

    Status(int id) {
        this.id = id;
    }
}