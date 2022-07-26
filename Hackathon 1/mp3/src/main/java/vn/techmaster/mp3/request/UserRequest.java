package vn.techmaster.mp3.request;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String id;
    @NotBlank(message = "tên không được để trống")
    private String firstName;
    @NotBlank(message = "tên không được để trống")
    private String lastName;
    @NotBlank(message = "tên không được để trống")
    private String avatar;
 }

