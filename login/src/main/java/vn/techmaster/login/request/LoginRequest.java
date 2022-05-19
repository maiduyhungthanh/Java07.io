package vn.techmaster.login.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "email cannot blank") @Email(message = "Invalid email") String email,

        @Size(min = 5, max = 20, message = "password length must between 5 and 20 characters") String password) {

}
