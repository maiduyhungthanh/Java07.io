package vn.techmaster.mp3.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.mp3.model.SongSinger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingerRequest {
    private String id;
    @NotNull
    @NotBlank(message = "tên không được để trống") String name;
    private String avatar;
    private List<SongSinger> songSingers = new ArrayList<>();

}
