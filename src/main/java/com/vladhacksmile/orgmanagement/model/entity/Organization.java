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
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Column(name = "name", nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id", nullable = false)
    private Coordinates coordinates; //Поле не может быть null
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Column(name = "annual_turnover", nullable = false)
    private Float annualTurnover; //Значение поля должно быть больше 0
    @Column(name = "type", nullable = false)
    private OrganizationType type; //Поле может быть null
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "official_address_id", referencedColumnName = "id")
    private Address officialAddress; //Поле может быть null
}
