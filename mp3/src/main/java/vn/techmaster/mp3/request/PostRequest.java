package vn.techmaster.mp3.request;
import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    String title;
    // @Column(length=50000)
    String content;
    String user_id;
}
