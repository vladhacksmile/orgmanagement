package com.vladhacksmile.orgmanagement.service;

import _8080.api.v1.orgservice.*;

public interface OrganizationService {

    ResultOrganization add(OrganizationDTO organizationDTO);

    ResultOrganization get(Long id);

    ResultOrganization put(OrganizationDTO organizationDTO);

    ResultOrganization delete(Long id);

    SearchResultOrganization getAll(int pageNum, int pageSize, String sortType, String sortColumn,
                                    String filterOperation, String filterField, String filterValue);

    SearchResultOrganization findSubstring(int pageNum, int pageSize, String sortType, String sortColumn, String substring);

    ResultInteger countLowerAnnualTurnover(Float annualTurnover);

    ResultListFloat findUniqueAnnualTurnover();
}
