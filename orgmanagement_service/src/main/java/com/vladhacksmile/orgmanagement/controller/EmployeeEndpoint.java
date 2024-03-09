package com.vladhacksmile.orgmanagement.controller;

import _8080.api.v1.orgservice.*;
import com.vladhacksmile.orgmanagement.mapper.EmployeeMapper;
import com.vladhacksmile.orgmanagement.model.ResultHelper;
import com.vladhacksmile.orgmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EmployeeEndpoint {

    private static final String NAMESPACE_URI = "8080/api/v1/orgservice";

    @Autowired
    private EmployeeService employeeService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllEmployeesRequest")
    @ResponsePayload
    public GetAllEmployeesResponse getAll(@RequestPayload GetAllEmployeesRequest getAllEmployeesRequest) {
        try {
            return EmployeeMapper.toGetAllEmployeesResponse(employeeService.getAll(getAllEmployeesRequest.getPageNum(),
                    getAllEmployeesRequest.getPageSize()));
        } catch (Exception e) {
            return EmployeeMapper.toGetAllEmployeesResponse(ResultHelper.createSearchResultEmployeeWithStatusAndDesc(Status.INTERNAL_ERROR, null));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllByOrganizationRequest")
    @ResponsePayload
    public GetAllByOrganizationResponse getAllByOrganization(@RequestPayload GetAllByOrganizationRequest getAllByOrganizationRequest) {
        try {
            return EmployeeMapper.toGetAllByOrganizationResponse(employeeService.getAllByOrganization(getAllByOrganizationRequest.getPageNum(),
                    getAllByOrganizationRequest.getPageSize(),
                    getAllByOrganizationRequest.getId()));
        } catch (Exception e) {
            return EmployeeMapper.toGetAllByOrganizationResponse(ResultHelper.createSearchResultEmployeeWithStatusAndDesc(Status.INTERNAL_ERROR, null));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEmployeeByIdRequest")
    @ResponsePayload
    public GetEmployeeByIdResponse get(@RequestPayload GetEmployeeByIdRequest getEmployeeByIdRequest) {
        try {
            return EmployeeMapper.toGetEmployeeByIdResponse(employeeService.get(getEmployeeByIdRequest.getId()));
        } catch (Exception e) {
            return EmployeeMapper.toGetEmployeeByIdResponse(ResultHelper.createEmployeeWithStatus(Status.INTERNAL_ERROR));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddEmployeeRequest")
    @ResponsePayload
    public AddEmployeeResponse add(@RequestPayload AddEmployeeRequest addEmployeeRequest) {
        try {
            return EmployeeMapper.toAddEmployeeResponse(employeeService.add(addEmployeeRequest.getEmployee()));
        } catch (Exception e) {
            return EmployeeMapper.toAddEmployeeResponse(ResultHelper.createEmployeeWithStatus(Status.INTERNAL_ERROR));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PutEmployeeRequest")
    @ResponsePayload
    public PutEmployeeResponse put(@RequestPayload PutEmployeeRequest putEmployeeRequest) {
        try {
            return EmployeeMapper.toPutEmployeeResponse(employeeService.put(putEmployeeRequest.getEmployee()));
        } catch (Exception e) {
            return EmployeeMapper.toPutEmployeeResponse(ResultHelper.createEmployeeWithStatus(Status.INTERNAL_ERROR));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteEmployeeByIdRequest")
    @ResponsePayload
    public DeleteEmployeeByIdResponse delete(@RequestPayload DeleteEmployeeByIdRequest deleteEmployeeByIdRequest) {
        try {
            return EmployeeMapper.toDeleteEmployeeByIdResponse(employeeService.delete(deleteEmployeeByIdRequest.getId()));
        } catch (Exception e) {
            return EmployeeMapper.toDeleteEmployeeByIdResponse(ResultHelper.createEmployeeWithStatus(Status.INTERNAL_ERROR));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "MigrateEmployeesRequest")
    @ResponsePayload
    public MigrateEmployeesResponse migrateEmployees(@RequestPayload MigrateEmployeesRequest migrateEmployeesRequest) {
        try {
            return EmployeeMapper.toMigrateEmployeesResponse(employeeService.migrateEmployees(migrateEmployeesRequest.getOrganizationId1(),
                    migrateEmployeesRequest.getOrganizationId2()));
        } catch (Exception e) {
            return EmployeeMapper.toMigrateEmployeesResponse(ResultHelper.createIntegerWithStatus(Status.INTERNAL_ERROR));
        }
    }
}
