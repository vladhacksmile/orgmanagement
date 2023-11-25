package com.vladhacksmile.orgmanagement.repository;

import com.vladhacksmile.orgmanagement.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long>,
        JpaSpecificationExecutor<Organization> {
    @Query("SELECT o.annualTurnover FROM Organization o WHERE o.annualTurnover < :annualTurnover")
    List<Float> findAnnualTurnoverLowerThan(@Param("annualTurnover") Float annualTurnover);

    @Query("SELECT DISTINCT o.annualTurnover FROM Organization o")
    List<Float> findUniqueAnnualTurnovers();
}