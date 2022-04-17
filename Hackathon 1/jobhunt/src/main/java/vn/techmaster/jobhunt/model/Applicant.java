package vn.techmaster.jobhunt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Applicant {
    private String name;
    private String email;
    private int phone;
    private String description;
}
