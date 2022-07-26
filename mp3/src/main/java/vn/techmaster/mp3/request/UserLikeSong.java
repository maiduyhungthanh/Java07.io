package vn.techmaster.mp3.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeSong {
    private String  id_song;
    private String id_user;
}
