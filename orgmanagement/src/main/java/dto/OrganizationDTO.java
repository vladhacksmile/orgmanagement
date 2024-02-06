package dto;

import lombok.*;
import model.entity.OrganizationType;

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
