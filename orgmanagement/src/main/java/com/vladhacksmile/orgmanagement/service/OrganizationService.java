package com.vladhacksmile.orgmanagement.service;

import com.vladhacksmile.orgmanagement.dto.OrganizationDTO;
import com.vladhacksmile.orgmanagement.model.entity.Organization;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;

import java.util.List;

public interface OrganizationService {
    Result<OrganizationDTO> add(OrganizationDTO organizationDTO);
    Result<OrganizationDTO> get(Long id);
    Result<OrganizationDTO> put(OrganizationDTO organizationDTO);
    Result<OrganizationDTO> delete(Long id);
    Result<SearchResult<Organization>> getAll(int pageNum, int pageSize, String sortType, String sortColumn,
                                              String filterOperation, String filterField, String filterValue);

    Result<SearchResult<Organization>> findSubstring(int pageNum, int pageSize, String sortType, String sortColumn, String substring);

    Result<Integer> countLowerAnnualTurnover(Float annualTurnover);

    Result<List<Float>> findUniqueAnnualTurnover();
}
