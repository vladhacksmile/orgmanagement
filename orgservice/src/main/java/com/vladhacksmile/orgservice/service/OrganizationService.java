package com.vladhacksmile.orgservice.service;

import com.vladhacksmile.orgservice.integration.OrganizationClient;
import com.vladhacksmile.orgservice.model.entity.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class OrganizationService {
    @Inject
    private OrganizationClient organizationClient;

    public void hire(Long organizationId, Employee employee) {
        return
    }
}
