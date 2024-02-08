package service.impl;

import dto.EmployeeDTO;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import lombok.Data;
import mapper.EmployeeMapper;
import model.entity.Employee;
import model.entity.Organization;
import model.result.Result;
import model.result.SearchResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.ejb3.annotation.Pool;
import service.EmployeeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static model.result.Result.*;
import static model.result.Status.INCORRECT_PARAMS;
import static model.result.Status.NOT_FOUND;
import static model.result.StatusDescription.*;

@Data
@Remote(EmployeeServiceImpl.class)
@Stateless(name = "EmployeeService")
@Pool("slsb-strict-max-pool")
public class EmployeeServiceImpl implements EmployeeService {

    @PersistenceContext(unitName = "db_unit")
    private EntityManager entityManager;

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

        Organization organization = entityManager.find(Organization.class, employeeDTO.getOrganizationId());
        if (organization == null) {
            return createWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        Employee employee = new Employee(null, employeeDTO.getUserName(), employeeDTO.getFirstName(),
                employeeDTO.getLastName(), employeeDTO.getEmail(), organization.getId());

        entityManager.persist(employee);

        return createWithOk(EmployeeMapper.fromEntity(employee));
    }

    @Override
    public Result<EmployeeDTO> get(Long id) {
        if (id == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Employee employee = entityManager.find(Employee.class, id);
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

        Employee employee = entityManager.find(Employee.class, employeeDTO.getOrganizationId());

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
            employee.setUserName(employeeDTO.getUserName());
        }

        if (!Objects.equals(employee.getEmail(), employeeDTO.getEmail())) {
            employee.setEmail(employeeDTO.getEmail());
        }

        if (!Objects.equals(employee.getOrganizationId(), employeeDTO.getOrganizationId())) {
            Organization organization = entityManager.find(Organization.class, employeeDTO.getOrganizationId());
            if (organization == null) {
                return createWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
            }
            employee.setOrganizationId(organization.getId());
        }

        entityManager.persist(employee);

        return createWithOk(EmployeeMapper.fromEntity(employee));
    }

    @Override
    @Transactional
    public Result<EmployeeDTO> delete(Long id) {
        if (id == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Employee employee = entityManager.find(Employee.class, id);

        if (employee == null) {
            return createWithStatus(NOT_FOUND);
        }

        entityManager.remove(id);

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

        String sql = "SELECT e FROM Employee e ORDER BY e.id";
        Query query = entityManager.createQuery(sql);
        query.setMaxResults(pageSize);
        query.setFirstResult(pageNum * pageSize);
        List<Employee> employees = query.getResultList();
        if (CollectionUtils.isEmpty(employees)) {
            return createWithStatusAndDesc(NOT_FOUND, EMPLOYEE_NOT_FOUND);
        }

        return createWithOk(SearchResult.makeSearchResult(employees, employees.size(), query.getMaxResults() / pageSize, (long) query.getMaxResults()));
    }

    @Override
    public Result<SearchResult<Employee>> getAllByOrganization(int pageNum, int pageSize, Long organizationId) {
        if (pageNum < 1) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, PAGE_NUM_MUST_BE_POSITIVE);
        }

        if (pageSize < 1) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, PAGE_SIZE_MUST_BE_POSITIVE);
        }

        String sql = "SELECT e FROM Employee e WHERE e.organizationId = :organization_id ORDER BY e.id"; // todo
        Query query = entityManager.createQuery(sql);
        query.setMaxResults(pageSize);
        query.setFirstResult(pageNum * pageSize);
        query.setParameter("organization_id", organizationId);
        List<Employee> employees = query.getResultList();
        if (CollectionUtils.isEmpty(employees)) {
            return createWithStatusAndDesc(NOT_FOUND, EMPLOYEE_NOT_FOUND);
        }

        return createWithOk(SearchResult.makeSearchResult(employees, employees.size(), query.getMaxResults() / pageSize, (long) query.getMaxResults()));
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

        Organization organization1 = entityManager.find(Organization.class, organizationId1);
        if (organization1 == null) {
            return createWithStatusAndDesc(NOT_FOUND, FIRST_ORGANIZATION_NOT_FOUND);
        }

        Organization organization2 = entityManager.find(Organization.class, organizationId2);
        if (organization2 == null) {
            return createWithStatusAndDesc(NOT_FOUND, SECOND_ORGANIZATION_NOT_FOUND);
        }

        Query query = entityManager.createQuery("UPDATE Employee e SET e.organizationId = :newOrganizationId WHERE e.organizationId = :organizationId");
        query.setParameter("newOrganizationId", organizationId1);
        query.setParameter("organizationId", organizationId2);
        int updated = query.executeUpdate();
        if (updated == 0) {
            return createWithStatusAndDesc(NOT_FOUND, EMPLOYEES_NOT_FOUND);
        }

        return createWithOk(updated);
    }
}
