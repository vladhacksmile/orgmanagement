package com.vladhacksmile.orgmanagement.model.result;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult<T> {
    private Status status;
    private String description;
    private Integer pageNum;
    private Integer pageSize;
    private Integer pageTotal;
    private T object;
}
