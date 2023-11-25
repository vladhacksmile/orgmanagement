package com.vladhacksmile.orgmanagement.model;

import com.vladhacksmile.orgmanagement.model.result.Result;
import org.springframework.http.ResponseEntity;

public class ResponseMapper {
    public static <T> ResponseEntity<Result<T>> map(Result<T> result) {
        return new ResponseEntity<>(result, result.getStatus().getHttpStatus());
    }
}
