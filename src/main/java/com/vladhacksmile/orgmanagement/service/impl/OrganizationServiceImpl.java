package com.vladhacksmile.orgmanagement.service.impl;

import com.vladhacksmile.orgmanagement.dto.OrganizationDTO;
import com.vladhacksmile.orgmanagement.dto.SearchDTO;
import com.vladhacksmile.orgmanagement.model.entity.Address;
import com.vladhacksmile.orgmanagement.model.entity.Coordinates;
import com.vladhacksmile.orgmanagement.model.entity.Organization;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;
import com.vladhacksmile.orgmanagement.repository.AddressRepository;
import com.vladhacksmile.orgmanagement.repository.CoordinatesRepository;
import com.vladhacksmile.orgmanagement.repository.OrganizationRepository;
import com.vladhacksmile.orgmanagement.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import static com.vladhacksmile.orgmanagement.model.result.Result.*;
import static com.vladhacksmile.orgmanagement.model.result.SearchResult.makeSearchResult;
import static com.vladhacksmile.orgmanagement.model.result.Status.INCORRECT_PARAMS;
import static com.vladhacksmile.orgmanagement.model.result.Status.NOT_FOUND;
import static com.vladhacksmile.orgmanagement.model.result.StatusDescription.*;

// todo статус код для http от статуса в Result
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CoordinatesRepository coordinatesRepository;

    @Autowired
    private AddressRepository addressRepository;

//    @Autowired
//    private Clock clock;

    @Transactional
    public Result<Organization> add(OrganizationDTO organizationDTO) {
        if (organizationDTO.getId() != null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_MUST_BE_NULL);
        }

        if (organizationDTO.getCoordinatesId() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, COORDINATES_ID_IS_NULL);
        }

        if (organizationDTO.getOfficialAddressId() == null) {
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

        Address address = addressRepository.findById(organizationDTO.getOfficialAddressId()).orElse(null);
        if (address == null) {
            return createWithStatusAndDesc(NOT_FOUND, ADDRESS_NOT_FOUND);
        }

        Coordinates coordinates = coordinatesRepository.findById(organizationDTO.getCoordinatesId()).orElse(null);
        if (coordinates == null) {
            return createWithStatusAndDesc(NOT_FOUND, COORDINATES_NOT_FOUND);
        }

        Organization organization = new Organization(null, organizationDTO.getName(), coordinates,
                ZonedDateTime.now(), organizationDTO.getAnnualTurnover(), organizationDTO.getType(), address);
        organizationRepository.save(organization);

        return createWithOk(organization);
    }

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

        if (!Objects.equals(organization.getCoordinates().getId(), organizationDTO.getCoordinatesId())) {
            Coordinates coordinates = coordinatesRepository.findById(organizationDTO.getCoordinatesId()).orElse(null);

            if (coordinates == null) {
                return createWithStatusAndDesc(NOT_FOUND, COORDINATES_NOT_FOUND);
            }

            organization.setCoordinates(coordinates);
        }

        if (!Objects.equals(organization.getOfficialAddress().getId(), organizationDTO.getOfficialAddressId())) {
            Address address = addressRepository.findById(organizationDTO.getOfficialAddressId()).orElse(null);

            if (address == null) {
                return createWithStatusAndDesc(NOT_FOUND, ADDRESS_NOT_FOUND);
            }

            organization.setOfficialAddress(address);
        }

        organizationRepository.save(organization);

        return createWithOk(organization);
    }

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

    public SearchResult<Organization> getAll(SearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(searchDTO.getPageNum() - 1, searchDTO.getPageSize());
        Page<Organization> organizationsPage = organizationRepository.findAll(pageable);
        List<Organization> organizations = organizationsPage.stream().toList();
        return makeSearchResult(organizations, organizations.size(), organizationsPage.getTotalPages());
    }
}
