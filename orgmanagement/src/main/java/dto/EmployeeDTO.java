package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeeDTO {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private Long organizationId;
}
