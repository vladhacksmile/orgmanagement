package com.vladhacksmile.orgmanagement.controller;

import _8080.api.v1.orgservice.*;
import com.vladhacksmile.orgmanagement.mapper.EmployeeMapper;
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
    public GetAllEmployeesResponse getAll(@RequestPayload GetAllEmployeesRequest getAllEmployeesRequest) {
        return EmployeeMapper.toGetAllEmployeesResponse(employeeService.getAll(getAllEmployeesRequest.getPageNum(),
                getAllEmployeesRequest.getPageSize()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllByOrganizationRequest")
    @ResponsePayload
    public GetAllByOrganizationResponse getAllByOrganization(@RequestPayload GetAllByOrganizationRequest getAllByOrganizationRequest) {
        return EmployeeMapper.toGetAllByOrganizationResponse(employeeService.getAllByOrganization(getAllByOrganizationRequest.getPageNum(),
                getAllByOrganizationRequest.getPageSize(),
                getAllByOrganizationRequest.getId())) ;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEmployeeByIdRequest")
    @ResponsePayload
    public GetEmployeeByIdResponse get(@RequestPayload GetEmployeeByIdRequest getEmployeeByIdRequest) {
        return EmployeeMapper.toGetEmployeeByIdResponse(employeeService.get(getEmployeeByIdRequest.getId()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddEmployeeRequest")
    @ResponsePayload
    public AddEmployeeResponse add(@RequestPayload AddEmployeeRequest addEmployeeRequest) {
        return EmployeeMapper.toAddEmployeeResponse(employeeService.add(addEmployeeRequest.getEmployee()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PutEmployeeRequest")
    @ResponsePayload
    public PutEmployeeResponse put(@RequestPayload PutEmployeeRequest putEmployeeRequest) {
        return EmployeeMapper.toPutEmployeeResponse(employeeService.put(putEmployeeRequest.getEmployee()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteEmployeeByIdRequest")
    @ResponsePayload
    public DeleteEmployeeByIdResponse delete(@RequestPayload DeleteEmployeeByIdRequest deleteEmployeeByIdRequest) {
        return EmployeeMapper.toDeleteEmployeeByIdResponse(employeeService.delete(deleteEmployeeByIdRequest.getId()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "MigrateEmployeesRequest")
    @ResponsePayload
    public MigrateEmployeesResponse migrateEmployees(@RequestPayload MigrateEmployeesRequest migrateEmployeesRequest) {
        return EmployeeMapper.toMigrateEmployeesResponse(employeeService.migrateEmployees(migrateEmployeesRequest.getOrganizationId1(),
                migrateEmployeesRequest.getOrganizationId2()));
    }
}
