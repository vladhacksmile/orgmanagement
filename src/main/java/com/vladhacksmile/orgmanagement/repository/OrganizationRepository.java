package com.vladhacksmile.orgmanagement.repository;

import com.vladhacksmile.orgmanagement.model.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    @Override
    Page<Organization> findAll(Pageable pageable);
}