package com.vladhacksmile.orgmanagement.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchCriteria {
    private boolean reverseSort;
    private SearchOperation searchOperation;
    private String object;
    private String value;

    @Getter
    public enum SearchOperation {
        LIKE("like"),
        EQUAL("equal"),
        GREATER("greater"),
        LESS("less"),
        GREATER_OR_EQUAL("greater_or_equal"),
        LESS_OR_EQUAL("less_or_equal");

        private final String name;

        SearchOperation(String name) {
            this.name = name;
        }

        public static SearchOperation find(String name) {
            for (SearchOperation operation: values()) {
                if (name.equalsIgnoreCase(operation.getName())) {
                    return operation;
                }
            }
            return null;
        }
    }
}
