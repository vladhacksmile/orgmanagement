package com.vladhacksmile.orgmanagement.repository;

import com.vladhacksmile.orgmanagement.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findAllByOrganizationId(Long organizationId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.organizationId = :newOrganizationId WHERE e.organizationId = :organizationId")
    int updateOrganizationId(@Param("organizationId") Long organizationId, @Param("newOrganizationId") Long newOrganizationId);
}