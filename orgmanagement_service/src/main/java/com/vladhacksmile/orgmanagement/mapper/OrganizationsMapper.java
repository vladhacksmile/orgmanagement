package com.vladhacksmile.orgmanagement.mapper;

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


}