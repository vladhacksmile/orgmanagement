package com.vladhacksmile.orgmanagement.controller;

import _8080.api.v1.orgservice.*;
import com.vladhacksmile.orgmanagement.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@Endpoint
public class EmployeeEndpoint {

    private static final String NAMESPACE_URI = "8080/api/v1/orgservice";

    @Autowired
    private EmployeeService employeeService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllEmployeesRequest")
    @ResponsePayload
    public SearchResultEmployee getAll(@RequestPayload GetAllEmployeesRequest getAllEmployeesRequest) {
        return employeeService.getAll(getAllEmployeesRequest.getPageNum(),
                getAllEmployeesRequest.getPageSize());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllByOrganizationRequest")
    @ResponsePayload
    public SearchResultEmployee getAllByOrganization(@RequestPayload GetAllByOrganizationRequest getAllByOrganizationRequest) {
        return employeeService.getAllByOrganization(getAllByOrganizationRequest.getPageNum(), getAllByOrganizationRequest.getPageSize(),
                getAllByOrganizationRequest.getId());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEmployeeByIdRequest")
    @ResponsePayload
    public ResultEmployee get(@RequestPayload GetEmployeeByIdRequest getEmployeeByIdRequest) {
        return employeeService.get(getEmployeeByIdRequest.getId());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddEmployeeRequest")
    @ResponsePayload
    public ResultEmployee add(@RequestPayload AddEmployeeRequest addEmployeeRequest) {
        return employeeService.add(addEmployeeRequest.getEmployee());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PutEmployeeRequest")
    @ResponsePayload
    public ResultEmployee put(@RequestPayload PutEmployeeRequest putEmployeeRequest) {
        return employeeService.put(putEmployeeRequest.getEmployee());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteEmployeeByIdRequest")
    @ResponsePayload
    public ResultEmployee delete(@RequestPayload DeleteEmployeeByIdRequest deleteEmployeeByIdRequest) {
        return employeeService.delete(deleteEmployeeByIdRequest.getId());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "MigrateEmployeesRequest")
    @ResponsePayload
    public ResultInteger migrateEmployees(@RequestPayload MigrateEmployeesRequest migrateEmployeesRequest) {
        return employeeService.migrateEmployees(migrateEmployeesRequest.getOrganizationId1(), migrateEmployeesRequest.getOrganizationId2());
    }
}
