package com.vladhacksmile.orgmanagement.service.impl;

import _8080.api.v1.orgservice.*;
import com.vladhacksmile.orgmanagement.model.entity.Organization;
import com.vladhacksmile.orgmanagement.repository.OrganizationRepository;
import com.vladhacksmile.orgmanagement.repository.OrganizationSpecification;
import com.vladhacksmile.orgmanagement.repository.SearchCriteria;
import com.vladhacksmile.orgmanagement.service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import static _8080.api.v1.orgservice.Status.*;
import static com.vladhacksmile.orgmanagement.model.ResultHelper.*;
import static com.vladhacksmile.orgmanagement.model.result.StatusDescription.*;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public ResultOrganization add(OrganizationDTO organizationDTO) {
        if (organizationDTO.getId() > 0) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, ID_MUST_BE_NULL);
        }

        if (organizationDTO.getCoordinateX() == 0) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, COORDINATE_X_IS_NULL);
        }

        if (organizationDTO.getCoordinateX() < -963) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, COORDINATE_X_IS_INCORRECT);
        }

        if (organizationDTO.getCoordinateY() == 0) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, COORDINATE_Y_IS_NULL);
        }

        if (organizationDTO.getOfficialAddress() == null) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, ADDRESS_ID_IS_NULL);
        }

        if (organizationDTO.getName() == null || organizationDTO.getName().isEmpty()) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, NAME_IS_NULL);
        }

        if (organizationDTO.getType() == null) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, ORGANIZATION_TYPE_IS_NULL);
        }

        if (organizationDTO.getAnnualTurnover() == 0) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, ANNUAL_TURNOVER_IS_NULL);
        }

        if (organizationDTO.getAnnualTurnover() < 1) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, ANNUAL_TURNOVER_IS_INCORRECT);
        }

        Organization organization = organizationRepository.save(new Organization(null, organizationDTO.getName(), organizationDTO.getCoordinateX(),
                organizationDTO.getCoordinateY(), ZonedDateTime.now(), organizationDTO.getAnnualTurnover(),
                com.vladhacksmile.orgmanagement.model.entity.OrganizationType.valueOf(organizationDTO.getType().name()), organizationDTO.getOfficialAddress()));

        return createOrganizationWithOk(convert(organization));
    }

    @Override
    public ResultOrganization get(Long id) {
        if (id == null) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Organization organization = organizationRepository.findById(id).orElse(null);
        if (organization == null) {
            return createOrganizationWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        return createOrganizationWithOk(convert(organization));
    }

    @Override
    @Transactional
    public ResultOrganization put(OrganizationDTO organizationDTO) {
        if (organizationDTO == null) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, ORGANIZATION_IS_NULL);
        }

        if (organizationDTO.getId() == 0) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Organization organization = organizationRepository.findById(organizationDTO.getId()).orElse(null);

        if (organization == null) {
            return createOrganizationWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        if (!Objects.equals(organization.getName(), organizationDTO.getName())) {
            organization.setName(organizationDTO.getName());
        }

        if (!Objects.equals(organization.getAnnualTurnover(), organizationDTO.getAnnualTurnover())) {
            organization.setAnnualTurnover(organizationDTO.getAnnualTurnover());
        }

        if (!Objects.equals(organization.getType().name(), organizationDTO.getType().name())) {
            organization.setType(com.vladhacksmile.orgmanagement.model.entity.OrganizationType.valueOf(organizationDTO.getType().name()));
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

        return createOrganizationWithOk(convert(organization));
    }

    @Override
    @Transactional
    public ResultOrganization delete(Long id) {
        if (id == null) {
            return createOrganizationWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Organization organization = organizationRepository.findById(id).orElse(null);

        if (organization == null) {
            return createOrganizationWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        organizationRepository.deleteById(id);

        return createOrganizationWithOk(convert(organization));
    }

    @Override
    public SearchResultOrganization getAll(int pageNum, int pageSize, String sortType, String sortColumn,
                                           String filterOperation, String filterField, String filterValue) {
        if (pageNum < 1) {
            return createSearchResultOrganizationWithStatusAndDesc(INCORRECT_PARAMS, PAGE_NUM_MUST_BE_POSITIVE);
        }

        if (pageSize < 1) {
            return createSearchResultOrganizationWithStatusAndDesc(INCORRECT_PARAMS, PAGE_SIZE_MUST_BE_POSITIVE);
        }

        if (!sortType.equalsIgnoreCase("ASC") && !sortType.equalsIgnoreCase("DESC")) {
            return createSearchResultOrganizationWithStatusAndDesc(INCORRECT_PARAMS, INCORRECT_SORT_TYPE);
        }

        if (StringUtils.isNotEmpty(sortColumn) && !validateColumns(sortColumn)) {
            return createSearchResultOrganizationWithStatusAndDesc(INCORRECT_PARAMS, INCORRECT_SORT_COLUMN);
        }

        if (StringUtils.isNotEmpty(filterField) && !validateColumns(filterField)) {
            return createSearchResultOrganizationWithStatusAndDesc(INCORRECT_PARAMS, INCORRECT_FILTER_FIELD);
        }

        SearchCriteria.SearchOperation searchOperation = null;
        if (StringUtils.isNotEmpty(filterField)) {
            searchOperation = SearchCriteria.SearchOperation.find(filterOperation);
            if (searchOperation == null) {
                return createSearchResultOrganizationWithStatusAndDesc(NOT_FOUND, FILTER_OPERATION_NOT_FOUND);
            }
        }
        boolean defaultSort = sortType.equalsIgnoreCase("ASC");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                StringUtils.isNotEmpty(sortColumn) ? Sort.by(defaultSort ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn) : Sort.unsorted());
        Page<Organization> organizationsPage;
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setReverseSort(sortType.equalsIgnoreCase("DESC"));
        searchCriteria.setSearchOperation(searchOperation);
        searchCriteria.setObject(filterField);
        searchCriteria.setValue(filterValue);
        organizationsPage = organizationRepository.findAll(new OrganizationSpecification(searchCriteria), pageable);

        List<Organization> organizations = organizationsPage.stream().toList();
        if (CollectionUtils.isEmpty(organizations)) {
            return createSearchResultOrganizationWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        List<OrganizationDTO> organizationDTOS = new ArrayList<>();
        for (Organization organization: organizations) {
            organizationDTOS.add(convert(organization));
        }

        return createOrganizationSearchResult(OK, null, organizationDTOS, organizations.size(), organizationsPage.getTotalPages(), organizationsPage.getTotalElements());
    }

    @Override
    public SearchResultOrganization findSubstring(int pageNum, int pageSize, String sortType, String sortColumn, String substring) {
        if (StringUtils.isEmpty(substring)) {
            return createSearchResultOrganizationWithStatusAndDesc(INCORRECT_PARAMS, SUBSTRING_IS_NULL);
        }
        return getAll(pageNum, pageSize, sortType, sortColumn, SearchCriteria.SearchOperation.LIKE.getName(), "name", substring);
    }

    @Override
    public ResultInteger countLowerAnnualTurnover(Float annualTurnover) {
        if (annualTurnover == null) {
            return createIntegerWithStatusAndDesc(INCORRECT_PARAMS, ANNUAL_TURNOVER_IS_NULL);
        }

        List<Float> values = organizationRepository.findAnnualTurnoverLowerThan(annualTurnover);
        if (CollectionUtils.isEmpty(values)) {
            return createIntegerWithStatusAndDesc(NOT_FOUND, FILTER_OPERATION_NOT_FOUND);
        }

        return createIntegerWithOk(values.size());
    }

    @Override
    public ResultListFloat findUniqueAnnualTurnover() {
        List<Float> values = organizationRepository.findUniqueAnnualTurnovers();
        if (CollectionUtils.isEmpty(values)) {
            return createListFloatWithStatus(NOT_FOUND);
        }

        return createListFloatWithOk(values);
    }

    private boolean validateColumns(String column) {
        return column.equalsIgnoreCase("id") || column.equalsIgnoreCase("name") ||
                column.equalsIgnoreCase("coordinateX") || column.equalsIgnoreCase("coordinateY")
                || column.equalsIgnoreCase("annualTurnover")
                || column.equalsIgnoreCase("creationDate")
                || column.equalsIgnoreCase("type") || column.equalsIgnoreCase("officialAddress");
    }

    public static OrganizationDTO convert(Organization organization) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(organization.getId());
        organizationDTO.setName(organization.getName());
        organizationDTO.setCoordinateX(organization.getCoordinateX());
        organizationDTO.setCoordinateY(organization.getCoordinateY());
        organizationDTO.setCreationDate(convertToXMLGregorianCalendar(organization.getCreationDate()));
        organizationDTO.setAnnualTurnover(organization.getAnnualTurnover());
        organizationDTO.setType(organization.getType() != null ? OrganizationType.valueOf(organization.getType().name()) : null); // todo npe
        organizationDTO.setOfficialAddress(organization.getOfficialAddress());

        return organizationDTO;
    }

    public static XMLGregorianCalendar convertToXMLGregorianCalendar(ZonedDateTime zonedDateTime) {
        try {
            if (zonedDateTime == null) {
                return null;
            }
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
            return datatypeFactory.newXMLGregorianCalendar(GregorianCalendar.from(zonedDateTime));
        } catch (DatatypeConfigurationException e) {
            return null;
        }
    }
}
