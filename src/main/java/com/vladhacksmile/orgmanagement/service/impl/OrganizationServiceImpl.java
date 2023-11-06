package com.vladhacksmile.orgmanagement.service.impl;

import com.vladhacksmile.orgmanagement.model.entity.Organization;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.service.OrganizationService;
import org.springframework.stereotype.Service;

import static com.vladhacksmile.orgmanagement.model.result.Result.createWithOk;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    public Result<Organization> add(Organization organization) {
        return createWithOk(organization);
    }
}
