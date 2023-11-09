package com.vladhacksmile.orgmanagement.dto;

import com.vladhacksmile.orgmanagement.model.entity.OrganizationType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationDTO {
    private Long id;
    private String name;
    private Long coordinatesId;
    private Float annualTurnover;
    private OrganizationType type;
    private Long officialAddressId;
}
