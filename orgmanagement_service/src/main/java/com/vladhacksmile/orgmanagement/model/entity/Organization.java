package com.vladhacksmile.orgmanagement.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity(name = "Organization")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Column(name = "name", nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Column(name = "coordinateX", nullable = false)
    private Long coordinateX; //Поле не может быть null
    @Column(name = "coordinateY", nullable = false)
    private Long coordinateY; //Поле не может быть null
    @Column(name = "creationDate", nullable = false)
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Column(name = "annualTurnover", nullable = false)
    private Float annualTurnover; //Значение поля должно быть больше 0
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private OrganizationType type; //Поле может быть null
    @Column(name = "officialAddress", nullable = false)
    private String officialAddress; //Поле может быть null
}
