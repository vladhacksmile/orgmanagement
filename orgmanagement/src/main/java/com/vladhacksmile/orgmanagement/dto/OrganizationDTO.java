package com.vladhacksmile.orgmanagement.dto;

import com.vladhacksmile.orgmanagement.model.entity.OrganizationType;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlRootElement
public class OrganizationDTO {
    private Long id;
    private String name;
    private Long coordinateX;
    private Long coordinateY;
    private Float annualTurnover;
    private OrganizationType type;
    private String officialAddress;
}
