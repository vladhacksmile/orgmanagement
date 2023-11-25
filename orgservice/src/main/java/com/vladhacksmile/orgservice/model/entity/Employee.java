package com.vladhacksmile.orgservice.model.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private Long organizationId;
}
