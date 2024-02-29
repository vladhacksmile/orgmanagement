package com.vladhacksmile.orgmanagement.service;

import _8080.api.v1.orgservice.EmployeeDTO;
import _8080.api.v1.orgservice.ResultEmployee;
import _8080.api.v1.orgservice.ResultInteger;
import _8080.api.v1.orgservice.SearchResultEmployee;

public interface EmployeeService {
    ResultEmployee add(EmployeeDTO employeeDTO);
    ResultEmployee get(Long id);
    ResultEmployee put(EmployeeDTO employeeDTO);
    ResultEmployee delete(Long id);
    SearchResultEmployee getAll(int pageNum, int pageSize);
    SearchResultEmployee getAllByOrganization(int pageNum, int pageSize, Long organizationId);
    ResultInteger migrateEmployees(Long organizationId1, Long organizationId2);
}
