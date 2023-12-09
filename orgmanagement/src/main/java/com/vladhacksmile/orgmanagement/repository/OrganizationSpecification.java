package com.vladhacksmile.orgmanagement.repository;

import com.vladhacksmile.orgmanagement.model.entity.Organization;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class OrganizationSpecification implements Specification<Organization> {

    private final SearchCriteria searchCriteria;

    public OrganizationSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (searchCriteria.getSearchOperation() != null && StringUtils.isNotEmpty(searchCriteria.getObject()) && StringUtils.isNotEmpty(searchCriteria.getValue())) {
            String lowerObject = searchCriteria.getObject().toLowerCase();
            String lowerValue = searchCriteria.getValue().toLowerCase();
            switch (searchCriteria.getSearchOperation()) {
                case GREATER -> {
                    return builder.greaterThan(root.get(lowerObject), lowerValue);
                }
                case LESS -> {
                    return builder.lessThan(root.get(lowerObject), lowerValue);
                }
                case LIKE -> {
                    return builder.like(root.get(lowerObject), lowerValue);
                }
                case EQUAL -> {
                    return builder.equal(root.get(lowerObject), lowerValue);
                }
                case GREATER_OR_EQUAL -> {
                    return builder.greaterThanOrEqualTo(root.get(lowerObject), lowerValue);
                }
                case LESS_OR_EQUAL -> {
                    return builder.lessThanOrEqualTo(root.get(lowerObject), lowerValue);
                }
                default -> {
                    return null;
                }
            }
        }
        return null;
    }
}