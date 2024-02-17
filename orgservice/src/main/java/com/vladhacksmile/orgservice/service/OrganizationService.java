package com.vladhacksmile.orgservice.service;

import com.vladhacksmile.orgservice.integration.OrganizationClient;
import com.vladhacksmile.orgservice.model.entity.Employee;
import com.vladhacksmile.orgservice.model.entity.OrganizationDTO;
import com.vladhacksmile.orgservice.model.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.vladhacksmile.orgservice.model.result.Result.createWithOk;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationClient organizationClient;

    public Result<?> hire(Long organizationId, Employee employee) {
        employee.setOrganizationId(organizationId);
        Result<Employee> employeeAddResult = organizationClient.addEmployee(employee);
        if (employeeAddResult.isError()) {
            return employeeAddResult;
        }

        return createWithOk();
    }

    public Result<?> acquise(Long organizationId1, Long organizationId2) {
        Result<Integer> migrateEmployeesResult = organizationClient.migrateEmployees(organizationId1, organizationId2);
        if (migrateEmployeesResult.isError()) {
            return migrateEmployeesResult;
        }

        Result<OrganizationDTO> deleteOrganizationResult = organizationClient.deleteOrganizationById(organizationId1);
        if (deleteOrganizationResult.isError()) {
            return deleteOrganizationResult;
        }

        return createWithOk();
    }
}
