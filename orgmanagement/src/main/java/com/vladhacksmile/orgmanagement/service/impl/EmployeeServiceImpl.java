package com.vladhacksmile.orgmanagement.service.impl;

import com.vladhacksmile.orgmanagement.dto.EmployeeDTO;
import com.vladhacksmile.orgmanagement.mapper.EmployeeMapper;
import com.vladhacksmile.orgmanagement.model.entity.Employee;
import com.vladhacksmile.orgmanagement.model.entity.Organization;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;
import com.vladhacksmile.orgmanagement.repository.EmployeeRepository;
import com.vladhacksmile.orgmanagement.repository.OrganizationRepository;
import com.vladhacksmile.orgmanagement.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static com.vladhacksmile.orgmanagement.model.result.Result.*;
import static com.vladhacksmile.orgmanagement.model.result.SearchResult.makeSearchResult;
import static com.vladhacksmile.orgmanagement.model.result.Status.INCORRECT_PARAMS;
import static com.vladhacksmile.orgmanagement.model.result.Status.NOT_FOUND;
import static com.vladhacksmile.orgmanagement.model.result.StatusDescription.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public Result<EmployeeDTO> add(EmployeeDTO employeeDTO) {
        if (employeeDTO.getId() != null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_MUST_BE_NULL);
        }

        if (StringUtils.isEmpty(employeeDTO.getUserName())) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, USER_NAME_IS_NULL);
        }

        if (StringUtils.isEmpty(employeeDTO.getFirstName())) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, FIRST_NAME_IS_NULL);
        }

        if (StringUtils.isEmpty(employeeDTO.getLastName())) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, LAST_NAME_IS_NULL);
        }

        if (StringUtils.isEmpty(employeeDTO.getEmail())) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, EMAIL_IS_NULL);
        }

        if (employeeDTO.getOrganizationId() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ORGANIZATION_ID_IS_NULL);
        }

        Organization organization = organizationRepository.findById(employeeDTO.getOrganizationId()).orElse(null);
        if (organization == null) {
            return createWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        Employee employee = employeeRepository.save(new Employee(null, employeeDTO.getUserName(), employeeDTO.getFirstName(),
                employeeDTO.getLastName(), employeeDTO.getEmail(), employeeDTO.getOrganizationId()));

        return createWithOk(EmployeeMapper.fromEntity(employee));
    }

    @Override
    public Result<EmployeeDTO> get(Long id) {
        if (id == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            return createWithStatusAndDesc(NOT_FOUND, EMPLOYEE_NOT_FOUND);
        }

        return createWithOk(EmployeeMapper.fromEntity(employee));
    }

    @Override
    @Transactional
    public Result<EmployeeDTO> put(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, EMPLOYEE_IS_NULL);
        }

        if (employeeDTO.getId() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Employee employee = employeeRepository.findById(employeeDTO.getId()).orElse(null);

        if (employee == null) {
            return createWithStatusAndDesc(NOT_FOUND, EMPLOYEE_NOT_FOUND);
        }

        if (!Objects.equals(employee.getFirstName(), employeeDTO.getFirstName())) {
            employee.setFirstName(employeeDTO.getFirstName());
        }

        if (!Objects.equals(employee.getLastName(), employeeDTO.getLastName())) {
            employee.setLastName(employeeDTO.getLastName());
        }

        if (!Objects.equals(employee.getUserName(), employeeDTO.getUserName())) {
            employee.setUserName(employee.getUserName());
        }

        if (!Objects.equals(employee.getEmail(), employeeDTO.getEmail())) {
            employee.setEmail(employee.getEmail());
        }

        if (!Objects.equals(employee.getOrganizationId(), employeeDTO.getOrganizationId())) {
            Organization organization = organizationRepository.findById(employeeDTO.getOrganizationId()).orElse(null);
            if (organization == null) {
                return createWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
            }
            employee.setOrganizationId(employeeDTO.getOrganizationId());
        }

        employee = employeeRepository.save(employee);

        return createWithOk(EmployeeMapper.fromEntity(employee));
    }

    @Override
    @Transactional
    public Result<EmployeeDTO> delete(Long id) {
        if (id == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            return createWithStatus(NOT_FOUND);
        }

        employeeRepository.deleteById(id);

        return createWithOk(EmployeeMapper.fromEntity(employee));
    }

    @Override
    public Result<SearchResult<Employee>> getAll(int pageNum, int pageSize) {
        if (pageNum < 1) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, PAGE_NUM_MUST_BE_POSITIVE);
        }

        if (pageSize < 1) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, PAGE_SIZE_MUST_BE_POSITIVE);
        }

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Employee> employeesPage = employeeRepository.findAll(pageable);

        List<Employee> employees = employeesPage.stream().toList();
        if (CollectionUtils.isEmpty(employees)) {
            return createWithStatusAndDesc(NOT_FOUND, EMPLOYEE_NOT_FOUND);
        }

        return createWithOk(makeSearchResult(employees, employees.size(), employeesPage.getTotalPages()));
    }

    @Override
    @Transactional
    public Result<Integer> migrateEmployees(Long organizationId1, Long organizationId2) {
        if (organizationId1 == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, FIRST_ORGANIZATION_ID_IS_NULL);
        }

        if (organizationId2 == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, SECOND_ORGANIZATION_ID_IS_NULL);
        }

        Organization organization1 = organizationRepository.findById(organizationId1).orElse(null);

        if (organization1 == null) {
            return createWithStatusAndDesc(NOT_FOUND, FIRST_ORGANIZATION_NOT_FOUND);
        }

        Organization organization2 = organizationRepository.findById(organizationId2).orElse(null);

        if (organization2 == null) {
            return createWithStatusAndDesc(NOT_FOUND, SECOND_ORGANIZATION_NOT_FOUND);
        }

        int updated = employeeRepository.updateOrganizationId(organizationId1, organizationId2);
        if (updated == 0) {
            return createWithStatusAndDesc(NOT_FOUND, EMPLOYEES_NOT_FOUND);
        }

        return createWithOk(updated);
    }
}
