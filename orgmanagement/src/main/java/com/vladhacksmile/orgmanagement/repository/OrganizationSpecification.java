package com.vladhacksmile.orgmanagement.repository;

import com.vladhacksmile.orgmanagement.model.entity.Organization;
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
        if (searchCriteria.getSearchOperation() != null && searchCriteria.getObject() != null && searchCriteria.getValue() != null) {
            switch (searchCriteria.getSearchOperation()) {
                case GREATER -> {
                    return builder.greaterThan(root.get(searchCriteria.getObject()), searchCriteria.getValue());
                }
                case LESS -> {
                    return builder.lessThan(root.get(searchCriteria.getObject()), searchCriteria.getValue());
                }
                case LIKE -> {
                    return builder.like(builder.lower(root.get(searchCriteria.getObject())), "%" + searchCriteria.getValue() + "%");
                }
                case EQUAL -> {
                    return builder.equal(root.get(searchCriteria.getObject()), searchCriteria.getValue());
                }
                case GREATER_OR_EQUAL -> {
                    return builder.greaterThanOrEqualTo(root.get(searchCriteria.getObject()), searchCriteria.getValue());
                }
                case LESS_OR_EQUAL -> {
                    return builder.lessThanOrEqualTo(root.get(searchCriteria.getObject()), searchCriteria.getValue());
                }
                default -> {
                    return null;
                }
            }
        }
        return null;
    }
}