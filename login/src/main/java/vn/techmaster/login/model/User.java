package vn.techmaster.login.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String id;
    private String fullname;
    private String email;
    private String hashed_password;
    private State state;
   
}
