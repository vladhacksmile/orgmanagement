package com.vladhacksmile.orgmanagement.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "street", nullable = false)
    private String street; //Строка не может быть пустой, Поле не может быть null
    @Column(name = "zip_code")
    private String zipCode; //Поле может быть null
}