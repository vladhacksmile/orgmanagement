package com.vladhacksmile.orgmanagement.service;

import com.vladhacksmile.orgmanagement.dto.OrganizationDTO;
import com.vladhacksmile.orgmanagement.dto.SearchDTO;
import com.vladhacksmile.orgmanagement.model.entity.Organization;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;

public interface OrganizationService {
    Result<Organization> add(OrganizationDTO organizationDTO);
    Result<Organization> get(Long id);
    Result<Organization> put(OrganizationDTO organizationDTO);
    Result<Organization> delete(Long id);
    SearchResult<Organization> getAll(SearchDTO searchDTO);
}
