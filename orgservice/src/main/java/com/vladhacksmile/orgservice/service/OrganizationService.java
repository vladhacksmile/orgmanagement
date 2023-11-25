package com.vladhacksmile.orgservice.service;

import com.vladhacksmile.orgservice.integration.OrganizationClient;
import com.vladhacksmile.orgservice.model.entity.Employee;
import com.vladhacksmile.orgservice.model.entity.OrganizationDTO;
import com.vladhacksmile.orgservice.model.result.Result;

import javax.enterprise.context.ApplicationScoped;

import static com.vladhacksmile.orgservice.model.result.Result.createWithOk;

@ApplicationScoped
public class OrganizationService {
//    @Inject
//    private OrganizationClient organizationClient;

    OrganizationClient organizationClient = new OrganizationClient();

    public Result<?> hire(Long organizationId, Employee employee) {
        employee.setOrganizationId(organizationId);
        Result<Employee> employeeAddResult = organizationClient.addEmployee(employee);
        if (employeeAddResult.isError()) {
            throw new IllegalArgumentException(employeeAddResult.getDescription());
        }

        return createWithOk();
    }

    public Result<?> acquise(Long organizationId1, Long organizationId2) {
        Result<Integer> migrateEmployeesResult = organizationClient.migrateEmployees(organizationId1, organizationId2);
        if (migrateEmployeesResult.isError()) {
            throw new IllegalArgumentException(migrateEmployeesResult.getDescription());
        }

        Result<OrganizationDTO> deleteteOrganizationResult = organizationClient.deleteOrganizationById(organizationId1);
        if (deleteteOrganizationResult.isError()) {
            throw new IllegalArgumentException(deleteteOrganizationResult.getDescription());
        }

        return createWithOk();
    }
}
