package com.vladhacksmile.orgmanagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchDTO {
    private Integer pageNum;
    private Integer pageSize;
    private SortType sortType;
    private SortColumn sortColumn;
    private FilterField filterField;
    private String filterValue;

    public enum SortType {
        ASC, DESC;
    }

    public enum SortColumn {
        NAME, ANNUAL_TURNOVER, TYPE, ADDRESS;
    }

    public enum FilterField {
        NAME, ANNUAL_TURNOVER, TYPE, ADDRESS;
    }
}
