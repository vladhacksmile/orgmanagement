package com.vladhacksmile.orgmanagement.mapper;

import _8080.api.v1.orgservice.*;
import com.vladhacksmile.orgmanagement.dto.OrganizationDTO;
import com.vladhacksmile.orgmanagement.model.entity.Organization;


public class OrganizationsMapper {
    public static OrganizationDTO fromEntity(Organization organization) {
        if (organization == null) {
            return null;
        }
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(organization.getId());
        organizationDTO.setName(organization.getName());
        organizationDTO.setCoordinateX(organization.getCoordinateX());
        organizationDTO.setCoordinateY(organization.getCoordinateY());
        organizationDTO.setCreationDate(organization.getCreationDate());
        organizationDTO.setAnnualTurnover(organization.getAnnualTurnover());
        organizationDTO.setType(organization.getType());
        organizationDTO.setOfficialAddress(organization.getOfficialAddress());

        return organizationDTO;
    }

    public static GetAllOrganizationsResponse toGetAllOrganizationsResponse(SearchResultOrganization searchResultOrganization) {
        GetAllOrganizationsResponse result = new GetAllOrganizationsResponse();
        result.setDescription(searchResultOrganization.getDescription());
        result.setPageNum(searchResultOrganization.getPageNum());
        result.setPageSize(searchResultOrganization.getPageSize());
        result.setStatus(searchResultOrganization.getStatus());
        result.setPageTotal(searchResultOrganization.getPageTotal());
        result.setTotalElements(searchResultOrganization.getTotalElements());
        result.getObjects().addAll(searchResultOrganization.getObjects());

        return result;
    }

    public static GetOrganizationByIdResponse toGetOrganizationByIdRequest(ResultOrganization resultOrganization) {
        GetOrganizationByIdResponse result = new GetOrganizationByIdResponse();
        result.setDescription(resultOrganization.getDescription());
        result.setStatus(resultOrganization.getStatus());
        result.setObject(resultOrganization.getObject());
        return result;
    }

    public static AddOrganizationResponse toAddOrganizationResponse(ResultOrganization resultOrganization) {
        AddOrganizationResponse result = new AddOrganizationResponse();
        result.setDescription(resultOrganization.getDescription());
        result.setStatus(resultOrganization.getStatus());
        result.setObject(resultOrganization.getObject());
        return result;
    }

    public static PutOrganizationResponse toPutOrganizationResponse(ResultOrganization resultOrganization) {
        PutOrganizationResponse result = new PutOrganizationResponse();
        result.setDescription(resultOrganization.getDescription());
        result.setStatus(resultOrganization.getStatus());
        result.setObject(resultOrganization.getObject());
        return result;
    }

    public static DeleteOrganizationByIdResponse toDeleteOrganizationByIdResponse(ResultOrganization resultOrganization) {
        DeleteOrganizationByIdResponse result = new DeleteOrganizationByIdResponse();
        result.setDescription(resultOrganization.getDescription());
        result.setStatus(resultOrganization.getStatus());
        result.setObject(resultOrganization.getObject());
        return result;
    }

}