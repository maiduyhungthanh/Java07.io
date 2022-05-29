package vn.techmaster.mp3.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingerRequest {
    private String id;
    private String name;
    // private String avatar;
}
