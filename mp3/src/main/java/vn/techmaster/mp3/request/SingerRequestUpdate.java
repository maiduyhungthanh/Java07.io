package vn.techmaster.mp3.request;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingerRequestUpdate{  
    @NotBlank(message = "Tên không được để trống") String name;
    String avatar;
 }
