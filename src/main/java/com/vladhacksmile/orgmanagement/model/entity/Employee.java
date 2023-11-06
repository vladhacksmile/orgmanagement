package com.vladhacksmile.orgmanagement.model.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private Integer organizationId;
}
