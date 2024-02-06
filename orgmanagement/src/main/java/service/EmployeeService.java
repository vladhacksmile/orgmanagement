package service;

import dto.EmployeeDTO;
import model.entity.Employee;
import model.result.Result;
import model.result.SearchResult;

public interface EmployeeService {
    Result<EmployeeDTO> add(EmployeeDTO employeeDTO);
    Result<EmployeeDTO> get(Long id);
    Result<EmployeeDTO> put(EmployeeDTO employeeDTO);
    Result<EmployeeDTO> delete(Long id);
    Result<SearchResult<Employee>> getAll(int pageNum, int pageSize);
    Result<SearchResult<Employee>> getAllByOrganization(int pageNum, int pageSize, Long organizationId);
    Result<Integer> migrateEmployees(Long organizationId1, Long organizationId2);
}
