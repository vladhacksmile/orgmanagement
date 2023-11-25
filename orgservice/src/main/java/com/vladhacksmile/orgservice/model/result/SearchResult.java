package com.vladhacksmile.orgservice.model.result;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult<T> {
    private List<T> objects;
    private Integer pageSize;
    private Integer pageNum;
    private Integer pageTotal;

    public static <T> SearchResult<T> makeSearchResult(List<T> objects, Integer pageNum, Integer pageTotal) {
        return new SearchResult<>(objects, objects.size(), pageNum, pageTotal);
    }
}
