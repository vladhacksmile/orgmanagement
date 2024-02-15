package com.vladhacksmile.orgservice.model.entity;

import lombok.*;

import java.time.ZonedDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrganizationDTO {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long coordinateX; //Поле не может быть null
    private Long coordinateY; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле может быть null
    private String officialAddress; //Поле может быть null
}
