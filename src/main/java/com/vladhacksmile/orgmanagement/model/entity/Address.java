package com.vladhacksmile.orgmanagement.model.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private String street; //Строка не может быть пустой, Поле не может быть null
    private String zipCode; //Поле может быть null
}