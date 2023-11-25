package com.vladhacksmile.orgmanagement.service;

import com.vladhacksmile.orgmanagement.dto.EmployeeDTO;
import com.vladhacksmile.orgmanagement.model.entity.Employee;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;

public interface EmployeeService {
    Result<Employee> add(EmployeeDTO employeeDTO);
    Result<Employee> get(Long id);
    Result<Employee> put(EmployeeDTO employeeDTO);
    Result<Employee> delete(Long id);
    Result<SearchResult<Employee>> getAll(int pageNum, int pageSize);
}
