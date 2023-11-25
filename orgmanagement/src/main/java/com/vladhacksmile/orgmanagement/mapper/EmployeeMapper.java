package com.vladhacksmile.orgmanagement.mapper;

import com.vladhacksmile.orgmanagement.dto.EmployeeDTO;
import com.vladhacksmile.orgmanagement.model.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDTO fromEntity(Employee employee) {
        if (employee == null) {
            return null;
        }
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
