package com.vladhacksmile.orgmanagement.service;

import com.vladhacksmile.orgmanagement.dto.EmployeeDTO;
import com.vladhacksmile.orgmanagement.model.entity.Employee;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;

public interface EmployeeService {
    Result<EmployeeDTO> add(EmployeeDTO employeeDTO);
    Result<EmployeeDTO> get(Long id);
    Result<EmployeeDTO> put(EmployeeDTO employeeDTO);
    Result<EmployeeDTO> delete(Long id);
    Result<SearchResult<Employee>> getAll(int pageNum, int pageSize);
    Result<SearchResult<Employee>> getAllByOrganization(int pageNum, int pageSize, Long organizationId);
    Result<Integer> migrateEmployees(Long organizationId1, Long organizationId2);
}
