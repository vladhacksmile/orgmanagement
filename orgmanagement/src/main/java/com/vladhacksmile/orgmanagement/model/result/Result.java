package com.vladhacksmile.orgmanagement.model.result;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Status status;
    private String description;
    private T object;

    public Result(Status status) {
        this.status = status;
    }

    public Result(Status status, T object) {
        this.status = status;
        this.object = object;
    }

    public static <T> Result<T> createWithOk(T object) {
        return new Result<>(Status.OK, object);
    }

    public static <T> Result<T> createWithStatusAndDesc(Status status, String description) {
        return new Result<>(status, description, null);
    }

    public static <T> Result<T> createWithStatusAndDesc(Status status, StatusDescription statusDescription) {
        return new Result<>(status, statusDescription.name(), null);
    }

    public static <T> Result<T> createWithStatusAndDesc(Status status, String description, T object) {
        return new Result<>(status, object);
    }

    public static <T> Result<T> createWithStatus(Status status) {
        return new Result<>(status);
    }
}
