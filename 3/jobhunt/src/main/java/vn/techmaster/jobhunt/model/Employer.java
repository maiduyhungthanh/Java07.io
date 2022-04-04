package vn.techmaster.jobhunt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// public record Employer(String id,String name,String email) {
// }
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employer {
    private String id;
    private String name;
    private String email;
}