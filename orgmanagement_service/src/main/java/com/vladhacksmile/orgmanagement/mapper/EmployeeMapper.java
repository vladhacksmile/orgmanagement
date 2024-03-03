package com.vladhacksmile.orgmanagement.mapper;

import _8080.api.v1.orgservice.*;

public class EmployeeMapper {

    public static GetAllEmployeesResponse toGetAllEmployeesResponse(SearchResultEmployee searchResultEmployee) {
        GetAllEmployeesResponse result = new GetAllEmployeesResponse();
        result.setDescription(searchResultEmployee.getDescription());
        result.setPageNum(searchResultEmployee.getPageNum());
        result.setPageSize(searchResultEmployee.getPageSize());
        result.setStatus(searchResultEmployee.getStatus());
        result.setPageTotal(searchResultEmployee.getPageTotal());
        result.setTotalElements(searchResultEmployee.getTotalElements());
        result.getObjects().addAll(searchResultEmployee.getObjects());

        return result;
    }

    public static GetAllByOrganizationResponse toGetAllByOrganizationResponse(SearchResultEmployee searchResultEmployee) {
        GetAllByOrganizationResponse result = new GetAllByOrganizationResponse();
        result.setDescription(searchResultEmployee.getDescription());
        result.setPageNum(searchResultEmployee.getPageNum());
        result.setPageSize(searchResultEmployee.getPageSize());
        result.setStatus(searchResultEmployee.getStatus());
        result.setPageTotal(searchResultEmployee.getPageTotal());
        result.setTotalElements(searchResultEmployee.getTotalElements());
        result.getObjects().addAll(searchResultEmployee.getObjects());

        return result;
    }

    public static GetEmployeeByIdResponse toGetEmployeeByIdResponse(ResultEmployee resultEmployee) {
        GetEmployeeByIdResponse result = new GetEmployeeByIdResponse();
        result.setDescription(resultEmployee.getDescription());
        result.setStatus(resultEmployee.getStatus());
        result.setObject(resultEmployee.getObject());
        return result;
    }

    public static AddEmployeeResponse toAddEmployeeResponse(ResultEmployee resultEmployee) {
        AddEmployeeResponse result = new AddEmployeeResponse();
        result.setDescription(resultEmployee.getDescription());
        result.setStatus(resultEmployee.getStatus());
        result.setObject(resultEmployee.getObject());
        return result;
    }

    public static PutEmployeeResponse toPutEmployeeResponse(ResultEmployee resultEmployee) {
        PutEmployeeResponse result = new PutEmployeeResponse();
        result.setDescription(resultEmployee.getDescription());
        result.setStatus(resultEmployee.getStatus());
        result.setObject(resultEmployee.getObject());
        return result;
    }

    public static DeleteEmployeeByIdResponse toDeleteEmployeeByIdResponse(ResultEmployee resultEmployee) {
        DeleteEmployeeByIdResponse result = new DeleteEmployeeByIdResponse();
        result.setDescription(resultEmployee.getDescription());
        result.setStatus(resultEmployee.getStatus());
        result.setObject(resultEmployee.getObject());
        return result;
    }

    public static MigrateEmployeesResponse toMigrateEmployeesResponse(ResultInteger resultInteger) {
        MigrateEmployeesResponse result = new MigrateEmployeesResponse();
        result.setDescription(resultInteger.getDescription());
        result.setStatus(resultInteger.getStatus());
        result.setObject(resultInteger.getObject());
        return result;
    }

}
