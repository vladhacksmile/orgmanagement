package com.vladhacksmile.orgmanagement.model.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coordinates {
    private Float x; //Максимальное значение поля: 662, Поле не может быть null
    private Float y; //Поле не может быть null
}
