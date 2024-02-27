package com.vladhacksmile.orgmanagement.controller;

import _8080.api.v1.orgservice.GenericResult;
import _8080.api.v1.orgservice.GetAllEmployeesRequest;
import _8080.api.v1.orgservice.Status;
import com.vladhacksmile.orgmanagement.model.entity.Employee;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEmployees")
    @ResponsePayload
    public GenericResult getAll(@RequestPayload GetAllEmployeesRequest getAllEmployeesRequest) {
        Result<SearchResult<Employee>> searchResult = employeeService.getAll(getAllEmployeesRequest.getPageNum(),
                getAllEmployeesRequest.getPageSize());

        return convert(searchResult);
    }

    public <T> GenericResult convert(Result<T> result) {
        GenericResult genericResult = new GenericResult();
        genericResult.setObject(result.getObject());
        genericResult.setStatus(Status.valueOf(result.getStatus().name()));
        genericResult.setDescription(result.getDescription());

        return genericResult;
    }

//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllByOrganization")
//    @ResponsePayload
//    public Result<SearchResult<Employee>> getAllByOrganization(@RequestParam(name = "page_num", defaultValue = "1") int pageNum,
//                                                                 @RequestParam(name = "page_size", defaultValue = "10") int pageSize,
//                                                                               @PathVariable Long id) {
//        return employeeService.getAllByOrganization(pageNum, pageSize, id);
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "get")
//    @ResponsePayload
//    public Result<EmployeeDTO> get(@PathVariable Long id) {
//        return employeeService.get(id);
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "add")
//    @ResponsePayload
//    public Result<EmployeeDTO> add(EmployeeDTO employeeDTO) {
//        return employeeService.add(employeeDTO);
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "put")
//    @ResponsePayload
//    public Result<EmployeeDTO> put(@RequestBody EmployeeDTO employeeDTO) {
//        return employeeService.put(employeeDTO);
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "delete")
//    @ResponsePayload
//    public Result<EmployeeDTO> delete(@PathVariable Long id) {
//        return employeeService.delete(id);
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "migrateEmployees")
//    @ResponsePayload
//    public Result<Integer> migrateEmployees(@PathVariable Long organizationId1, @PathVariable Long organizationId2) {
//        return employeeService.migrateEmployees(organizationId1, organizationId2);
//    }
}
