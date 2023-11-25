package com.vladhacksmile.orgmanagement.service;

import com.vladhacksmile.orgmanagement.dto.OrganizationDTO;
import com.vladhacksmile.orgmanagement.model.entity.Organization;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;

import java.util.List;

public interface OrganizationService {
    Result<Organization> add(OrganizationDTO organizationDTO);
    Result<Organization> get(Long id);
    Result<Organization> put(OrganizationDTO organizationDTO);
    Result<Organization> delete(Long id);
    Result<SearchResult<Organization>> getAll(int pageNum, int pageSize, String sortType, String sortColumn,
                                              String filterOperation, String filterField, String filterValue);

    Result<SearchResult<Organization>> findSubstring(int pageNum, int pageSize, String field, String substring);

    Result<List<Float>> countLowerAnnualTurnover(Float annualTurnover);

    Result<List<Float>> findUniqueAnnualTurnover();
}
