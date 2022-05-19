package vn.techmaster.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestUpdate {
    private String name;
    private String phone;
    private String address;
}
