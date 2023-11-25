package com.vladhacksmile.orgmanagement.service.impl;

import com.vladhacksmile.orgmanagement.dto.OrganizationDTO;
import com.vladhacksmile.orgmanagement.model.entity.Organization;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;
import com.vladhacksmile.orgmanagement.repository.OrganizationRepository;
import com.vladhacksmile.orgmanagement.repository.OrganizationSpecification;
import com.vladhacksmile.orgmanagement.repository.SearchCriteria;
import com.vladhacksmile.orgmanagement.service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import static com.vladhacksmile.orgmanagement.model.result.Result.*;
import static com.vladhacksmile.orgmanagement.model.result.SearchResult.makeSearchResult;
import static com.vladhacksmile.orgmanagement.model.result.Status.INCORRECT_PARAMS;
import static com.vladhacksmile.orgmanagement.model.result.Status.NOT_FOUND;
import static com.vladhacksmile.orgmanagement.model.result.StatusDescription.*;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public Result<Organization> add(OrganizationDTO organizationDTO) {
        if (organizationDTO.getId() != null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_MUST_BE_NULL);
        }

        if (organizationDTO.getCoordinateX() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, COORDINATE_X_IS_NULL);
        }

        if (organizationDTO.getCoordinateY() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, COORDINATE_Y_IS_NULL);
        }

        if (organizationDTO.getOfficialAddress() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ADDRESS_ID_IS_NULL);
        }

        if (organizationDTO.getName() == null || organizationDTO.getName().isEmpty()) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, NAME_IS_NULL);
        }

        if (organizationDTO.getType() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ORGANIZATION_TYPE_IS_NULL);
        }

        if (organizationDTO.getAnnualTurnover() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ANNUAL_TURNOVER_IS_NULL);
        }


        Organization organization = organizationRepository.save(new Organization(null, organizationDTO.getName(), organizationDTO.getCoordinateX(),
                organizationDTO.getCoordinateY(), ZonedDateTime.now(), organizationDTO.getAnnualTurnover(),
                organizationDTO.getType(), organizationDTO.getOfficialAddress()));

        return createWithOk(organization);
    }

    @Override
    public Result<Organization> get(Long id) {
        if (id == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Organization organization = organizationRepository.findById(id).orElse(null);
        if (organization == null) {
            return createWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        return createWithOk(organization);
    }

    @Override
    @Transactional
    public Result<Organization> put(OrganizationDTO organizationDTO) {
        if (organizationDTO == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ORGANIZATION_IS_NULL);
        }

        if (organizationDTO.getId() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Organization organization = organizationRepository.findById(organizationDTO.getId()).orElse(null);

        if (organization == null) {
            return createWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        if (!Objects.equals(organization.getName(), organizationDTO.getName())) {
            organization.setName(organization.getName());
        }

        if (!Objects.equals(organization.getAnnualTurnover(), organizationDTO.getAnnualTurnover())) {
            organization.setAnnualTurnover(organizationDTO.getAnnualTurnover());
        }

        if (!Objects.equals(organization.getType(), organizationDTO.getType())) {
            organization.setType(organizationDTO.getType());
        }

        if (!Objects.equals(organization.getAnnualTurnover(), organizationDTO.getAnnualTurnover())) {
            organization.setAnnualTurnover(organizationDTO.getAnnualTurnover());
        }

        if (!Objects.equals(organization.getCoordinateX(), organizationDTO.getCoordinateX())) {
            organization.setCoordinateX(organizationDTO.getCoordinateX());
        }

        if (!Objects.equals(organization.getOfficialAddress(), organizationDTO.getOfficialAddress())) {
            organization.setOfficialAddress(organizationDTO.getOfficialAddress());
        }

        organizationRepository.save(organization);

        return createWithOk(organization);
    }

    @Override
    @Transactional
    public Result<Organization> delete(Long id) {
        if (id == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Organization organization = organizationRepository.findById(id).orElse(null);

        if (organization == null) {
            return createWithStatus(NOT_FOUND);
        }

        organizationRepository.deleteById(id);

        return createWithOk(organization);
    }

    @Override
    public Result<SearchResult<Organization>> getAll(int pageNum, int pageSize, String sortType, String sortColumn,
                                                     String filterOperation, String filterField, String filterValue) {
        if (pageNum < 1) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, PAGE_NUM_MUST_BE_POSITIVE);
        }

        if (pageSize < 1) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, PAGE_SIZE_MUST_BE_POSITIVE);
        }

        if (!sortType.equalsIgnoreCase("ASC") && !sortType.equalsIgnoreCase("DESC")) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, INCORRECT_SORT_TYPE);
        }

        if (StringUtils.isNotEmpty(sortColumn) && !validateColumns(sortColumn)) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, INCORRECT_SORT_COLUMN);
        }

        if (StringUtils.isNotEmpty(filterField) && !validateColumns(filterField)) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, INCORRECT_FILTER_FIELD);
        }

        SearchCriteria.SearchOperation searchOperation = null;
        if (StringUtils.isNotEmpty(filterField)) {
            searchOperation = SearchCriteria.SearchOperation.find(filterOperation);
            if (searchOperation == null) {
                return createWithStatusAndDesc(NOT_FOUND, FILTER_OPERATION_NOT_FOUND);
            }
        }

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Organization> organizationsPage;
        if (StringUtils.isNotEmpty(filterField) && searchOperation != null) {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setReverseSort(sortType.equalsIgnoreCase("DESC"));
            searchCriteria.setSearchOperation(searchOperation);
            searchCriteria.setObject(filterField);
            searchCriteria.setValue(filterValue);
            organizationsPage = organizationRepository.findAll(new OrganizationSpecification(searchCriteria), pageable);
        } else {
            organizationsPage = organizationRepository.findAll(pageable);
        }

        List<Organization> organizations = organizationsPage.stream().toList();
        if (CollectionUtils.isEmpty(organizations)) {
            return createWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        return createWithOk(makeSearchResult(organizations, organizations.size(), organizationsPage.getTotalPages()));
    }

    @Override
    public Result<SearchResult<Organization>> findSubstring(int pageNum, int pageSize, String field, String substring) {
        if (StringUtils.isEmpty(field)) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, FIELD_IS_NULL);
        }
        if (StringUtils.isEmpty(substring)) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, SUBSTRING_IS_NULL);
        }
        return getAll(pageNum, pageSize, "ASC", null, SearchCriteria.SearchOperation.LIKE.getName(), field, substring);
    }

    @Override
    public Result<List<Float>> countLowerAnnualTurnover(Float annualTurnover) {
        if (annualTurnover == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ANNUAL_TURNOVER_IS_NULL);
        }

        List<Float> values = organizationRepository.findAnnualTurnoverLowerThan(annualTurnover);
        if (CollectionUtils.isEmpty(values)) {
            return createWithStatus(NOT_FOUND);
        }

        return createWithOk(values);
    }

    @Override
    public Result<List<Float>> findUniqueAnnualTurnover() {
        List<Float> values = organizationRepository.findUniqueAnnualTurnovers();
        if (CollectionUtils.isEmpty(values)) {
            return createWithStatus(NOT_FOUND);
        }

        return createWithOk(values);
    }

    private boolean validateColumns(String column) {
        return column.equalsIgnoreCase("name") || column.equalsIgnoreCase("annual_turnover")
                || column.equalsIgnoreCase("type") || column.equalsIgnoreCase("address");
    }
}
