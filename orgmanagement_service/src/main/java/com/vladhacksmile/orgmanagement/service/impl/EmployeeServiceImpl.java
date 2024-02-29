package com.vladhacksmile.orgmanagement.service.impl;

import _8080.api.v1.orgservice.EmployeeDTO;
import _8080.api.v1.orgservice.ResultEmployee;
import _8080.api.v1.orgservice.ResultInteger;
import _8080.api.v1.orgservice.SearchResultEmployee;
import com.vladhacksmile.orgmanagement.model.entity.Employee;
import com.vladhacksmile.orgmanagement.model.entity.Organization;
import com.vladhacksmile.orgmanagement.repository.EmployeeRepository;
import com.vladhacksmile.orgmanagement.repository.OrganizationRepository;
import com.vladhacksmile.orgmanagement.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static _8080.api.v1.orgservice.Status.*;
import static com.vladhacksmile.orgmanagement.model.ResultHelper.*;
import static com.vladhacksmile.orgmanagement.model.result.StatusDescription.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public ResultEmployee add(EmployeeDTO employeeDTO) {
        if (employeeDTO.getId() == 0) {
            return createEmployeeWithStatusAndDesc(INCORRECT_PARAMS, ID_MUST_BE_NULL);
        }

        if (StringUtils.isEmpty(employeeDTO.getUserName())) {
            return createEmployeeWithStatusAndDesc(INCORRECT_PARAMS, USER_NAME_IS_NULL);
        }

        if (StringUtils.isEmpty(employeeDTO.getFirstName())) {
            return createEmployeeWithStatusAndDesc(INCORRECT_PARAMS, FIRST_NAME_IS_NULL);
        }

        if (StringUtils.isEmpty(employeeDTO.getLastName())) {
            return createEmployeeWithStatusAndDesc(INCORRECT_PARAMS, LAST_NAME_IS_NULL);
        }

        if (StringUtils.isEmpty(employeeDTO.getEmail())) {
            return createEmployeeWithStatusAndDesc(INCORRECT_PARAMS, EMAIL_IS_NULL);
        }

        if (employeeDTO.getOrganizationId() == 0) {
            return createEmployeeWithStatusAndDesc(INCORRECT_PARAMS, ORGANIZATION_ID_IS_NULL);
        }

        Organization organization = organizationRepository.findById(employeeDTO.getOrganizationId()).orElse(null);
        if (organization == null) {
            return createEmployeeWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        Employee employee = employeeRepository.save(new Employee(null, employeeDTO.getUserName(), employeeDTO.getFirstName(),
                employeeDTO.getLastName(), employeeDTO.getEmail(), organization.getId()));

        return createEmployeeWithOk(convert(employee));
    }

    @Override
    public ResultEmployee get(Long id) {
        if (id == null) {
            return createEmployeeWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            return createEmployeeWithStatusAndDesc(NOT_FOUND, EMPLOYEE_NOT_FOUND);
        }

        return createEmployeeWithOk(convert(employee));
    }

    @Override
    @Transactional
    public ResultEmployee put(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            return createEmployeeWithStatusAndDesc(INCORRECT_PARAMS, EMPLOYEE_IS_NULL);
        }

        if (employeeDTO.getId() == 0) {
            return createEmployeeWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Employee employee = employeeRepository.findById(employeeDTO.getId()).orElse(null);

        if (employee == null) {
            return createEmployeeWithStatusAndDesc(NOT_FOUND, EMPLOYEE_NOT_FOUND);
        }

        if (!Objects.equals(employee.getFirstName(), employeeDTO.getFirstName())) {
            employee.setFirstName(employeeDTO.getFirstName());
        }

        if (!Objects.equals(employee.getLastName(), employeeDTO.getLastName())) {
            employee.setLastName(employeeDTO.getLastName());
        }

        if (!Objects.equals(employee.getUserName(), employeeDTO.getUserName())) {
            employee.setUserName(employeeDTO.getUserName());
        }

        if (!Objects.equals(employee.getEmail(), employeeDTO.getEmail())) {
            employee.setEmail(employeeDTO.getEmail());
        }

        if (!Objects.equals(employee.getOrganizationId(), employeeDTO.getOrganizationId())) {
            Organization organization = organizationRepository.findById(employeeDTO.getOrganizationId()).orElse(null);
            if (organization == null) {
                return createEmployeeWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
            }
            employee.setOrganizationId(organization.getId());
        }

        employee = employeeRepository.save(employee);

        return createEmployeeWithOk(convert(employee));
    }

    @Override
    @Transactional
    public ResultEmployee delete(Long id) {
        if (id == null) {
            return createEmployeeWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            return createEmployeeWithStatus(NOT_FOUND);
        }

        employeeRepository.deleteById(id);

        return createEmployeeWithOk(convert(employee));
    }

    @Override
    public SearchResultEmployee getAll(int pageNum, int pageSize) {
        if (pageNum < 1) {
            return createSearchResultEmployeeWithStatusAndDesc(INCORRECT_PARAMS, PAGE_NUM_MUST_BE_POSITIVE);
        }

        if (pageSize < 1) {
            return createSearchResultEmployeeWithStatusAndDesc(INCORRECT_PARAMS, PAGE_SIZE_MUST_BE_POSITIVE);
        }

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Employee> employeesPage = employeeRepository.findAll(pageable);

        List<Employee> employees = employeesPage.stream().toList();
        if (CollectionUtils.isEmpty(employees)) {
            return createSearchResultEmployeeWithStatusAndDesc(NOT_FOUND, EMPLOYEE_NOT_FOUND);
        }

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee: employees) {
            employeeDTOS.add(convert(employee));
        }

        return createEmployeeSearchResult(OK, null, employeeDTOS, employees.size(), employeesPage.getTotalPages(), employeesPage.getTotalElements());
    }

    @Override
    public SearchResultEmployee getAllByOrganization(int pageNum, int pageSize, Long organizationId) {
        if (pageNum < 1) {
            return createSearchResultEmployeeWithStatusAndDesc(INCORRECT_PARAMS, PAGE_NUM_MUST_BE_POSITIVE);
        }

        if (pageSize < 1) {
            return createSearchResultEmployeeWithStatusAndDesc(INCORRECT_PARAMS, PAGE_SIZE_MUST_BE_POSITIVE);
        }

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<Employee> employeesPage = employeeRepository.findAllByOrganizationId(organizationId, pageable);

        List<Employee> employees = employeesPage.stream().toList();
        if (CollectionUtils.isEmpty(employees)) {
            return createSearchResultEmployeeWithStatusAndDesc(NOT_FOUND, EMPLOYEE_NOT_FOUND);
        }

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee: employees) {
            employeeDTOS.add(convert(employee));
        }

        return createEmployeeSearchResult(OK, null, employeeDTOS, employees.size(), employeesPage.getTotalPages(), employeesPage.getTotalElements());
    }

    @Override
    @Transactional
    public ResultInteger migrateEmployees(Long organizationId1, Long organizationId2) {
        if (organizationId1 == null) {
            return createIntegerWithStatusAndDesc(INCORRECT_PARAMS, FIRST_ORGANIZATION_ID_IS_NULL);
        }

        if (organizationId2 == null) {
            return createIntegerWithStatusAndDesc(INCORRECT_PARAMS, SECOND_ORGANIZATION_ID_IS_NULL);
        }

        Organization organization1 = organizationRepository.findById(organizationId1).orElse(null);

        if (organization1 == null) {
            return createIntegerWithStatusAndDesc(NOT_FOUND, FIRST_ORGANIZATION_NOT_FOUND);
        }

        Organization organization2 = organizationRepository.findById(organizationId2).orElse(null);

        if (organization2 == null) {
            return createIntegerWithStatusAndDesc(NOT_FOUND, SECOND_ORGANIZATION_NOT_FOUND);
        }

        int updated = employeeRepository.updateOrganizationId(organizationId1, organizationId2);
        if (updated == 0) {
            return createIntegerWithStatusAndDesc(NOT_FOUND, EMPLOYEES_NOT_FOUND);
        }

        return createIntegerWithOk(updated);
    }
}
