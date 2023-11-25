package com.vladhacksmile.orgmanagement.dto;

import com.vladhacksmile.orgmanagement.model.entity.OrganizationType;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrganizationDTO {
    private Long id;
    private String name;
    private Long coordinateX;
    private Long coordinateY;
    private ZonedDateTime creationDate;
    private Float annualTurnover;
    private OrganizationType type;
    private String officialAddress;
}
