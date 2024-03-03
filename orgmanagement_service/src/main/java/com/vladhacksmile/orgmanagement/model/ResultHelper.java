package com.vladhacksmile.orgmanagement.model;

import _8080.api.v1.orgservice.*;
import com.vladhacksmile.orgmanagement.model.entity.Employee;
import com.vladhacksmile.orgmanagement.model.result.StatusDescription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultHelper {
    public static ResultInteger createIntegerWithStatusAndDesc(Status status, StatusDescription statusDescription) {
        return createIntegerWithStatusAndDesc(status, statusDescription, 0);
    }

    public static ResultInteger createIntegerWithStatus(Status status) {
        return createIntegerWithStatusAndDesc(status, null, 0);
    }

    public static ResultInteger createIntegerWithOk(int value) {
        return createIntegerWithStatusAndDesc(Status.OK, null, value);
    }

    public static ResultInteger createIntegerWithStatusAndDesc(Status status, StatusDescription statusDescription, int value) {
        ResultInteger genericResult = new ResultInteger();
        genericResult.setStatus(status);
        genericResult.setDescription(statusDescription != null ? statusDescription.name() : null);
        genericResult.setObject(value);

        return genericResult;
    }

    public static ResultListFloat createListFloatWithStatusAndDesc(Status status, StatusDescription statusDescription) {
        return createListFloatWithStatusAndDesc(status, statusDescription, null);
    }

    public static ResultListFloat createListFloatWithStatus(Status status) {
        return createListFloatWithStatusAndDesc(status, null, null);
    }

    public static ResultListFloat createListFloatWithOk(List<Float> value) {
        return createListFloatWithStatusAndDesc(Status.OK, null, value);
    }

    public static ResultListFloat createListFloatWithStatusAndDesc(Status status, StatusDescription statusDescription, List<Float> value) {
        ResultListFloat genericResult = new ResultListFloat();
        genericResult.setStatus(status);
        genericResult.setDescription(statusDescription != null ? statusDescription.name() : null);
        genericResult.getObject().addAll(value);

        return genericResult;
    }

    public static ResultEmployee createEmployeeWithStatusAndDesc(Status status, StatusDescription statusDescription) {
        return createEmployeeWithStatusAndDesc(status, statusDescription, null);
    }

    public static ResultEmployee createEmployeeWithStatus(Status status) {
        return createEmployeeWithStatusAndDesc(status, null, null);
    }

    public static ResultEmployee createEmployeeWithOk(EmployeeDTO employeeDTO) {
        return createEmployeeWithStatusAndDesc(Status.OK, null, employeeDTO);
    }

    public static ResultEmployee createEmployeeWithStatusAndDesc(Status status, StatusDescription statusDescription, EmployeeDTO employeeDTO) {
        ResultEmployee genericResult = new ResultEmployee();
        genericResult.setStatus(status);
        genericResult.setDescription(statusDescription != null ? statusDescription.name() : null);
        genericResult.setObject(employeeDTO);

        return genericResult;
    }

    public static ResultOrganization createOrganizationWithStatusAndDesc(Status status, StatusDescription statusDescription) {
        return createOrganizationWithStatusAndDesc(status, statusDescription, null);
    }

    public static ResultOrganization createOrganizationWithStatus(Status status) {
        return createOrganizationWithStatusAndDesc(status, null, null);
    }

    public static ResultOrganization createOrganizationWithOk(OrganizationDTO organizationDTO) {
        return createOrganizationWithStatusAndDesc(Status.OK, null, organizationDTO);
    }

    public static ResultOrganization createOrganizationWithStatusAndDesc(Status status, StatusDescription statusDescription, OrganizationDTO organizationDTO) {
        ResultOrganization genericResult = new ResultOrganization();
        genericResult.setStatus(status);
        genericResult.setDescription(statusDescription != null ? statusDescription.name() : null);
        genericResult.setObject(organizationDTO);

        return genericResult;
    }

    public static GenericSearchResult createSearchResultWithStatusAndDesc(Status status, StatusDescription statusDescription) {
        return createSearchResult(status, statusDescription, 0, 0, 0L);
    }

    public static SearchResultOrganization createSearchResultOrganizationWithStatusAndDesc(Status status, StatusDescription statusDescription) {
        return createOrganizationSearchResult(status, statusDescription, Collections.emptyList(), 0, 0, 0L);
    }

    public static SearchResultOrganization createOrganizationSearchResult(Status status, StatusDescription statusDescription, List<OrganizationDTO> objects, Integer pageNum, Integer pageTotal, Long totalElements) {
        SearchResultOrganization genericSearchResult = new SearchResultOrganization();
        genericSearchResult.setStatus(status);
        genericSearchResult.setDescription(statusDescription != null ? statusDescription.name() : null);
        genericSearchResult.setPageNum(pageNum);
        genericSearchResult.setPageTotal(pageTotal);
        genericSearchResult.setTotalElements(totalElements);
        genericSearchResult.getObjects().addAll(objects);
        genericSearchResult.setPageSize(genericSearchResult.getObjects().size());
        return genericSearchResult;
    }

    public static SearchResultEmployee createSearchResultEmployeeWithStatusAndDesc(Status status, StatusDescription statusDescription) {
        return createEmployeeSearchResult(status, statusDescription, Collections.emptyList(), 0, 0, 0L);
    }

    public static SearchResultEmployee createEmployeeSearchResult(Status status, StatusDescription statusDescription, List<EmployeeDTO> objects, Integer pageNum, Integer pageTotal, Long totalElements) {
        SearchResultEmployee genericSearchResult = new SearchResultEmployee();
        genericSearchResult.setStatus(status);
        genericSearchResult.setDescription(statusDescription != null ? statusDescription.name() : null);
        genericSearchResult.setPageNum(pageNum);
        genericSearchResult.setPageTotal(pageTotal);
        genericSearchResult.setTotalElements(totalElements);
        genericSearchResult.getObjects().addAll(objects);
        genericSearchResult.setPageSize(genericSearchResult.getObjects().size());
        return genericSearchResult;
    }

    public static GenericSearchResult createSearchResult(Status status, StatusDescription statusDescription, Integer pageNum, Integer pageTotal, Long totalElements) {
        GenericSearchResult genericSearchResult = new GenericSearchResult();
        genericSearchResult.setStatus(status);
        genericSearchResult.setDescription(statusDescription != null ? statusDescription.name() : null);
        genericSearchResult.setPageNum(pageNum);
        genericSearchResult.setPageTotal(pageTotal);
        genericSearchResult.setTotalElements(totalElements);

        return genericSearchResult;
    }

    public static <T> List<Object> toListObject(List<T> list) {
        return new ArrayList<>(list);
    }

    public static EmployeeDTO convert(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setUserName(employee.getUserName());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setOrganizationId(employee.getOrganizationId());

        return employeeDTO;
    }
}
