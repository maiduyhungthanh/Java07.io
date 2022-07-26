package vn.techmaster.mp3.request;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.mp3.model.SongCategory;
import vn.techmaster.mp3.model.SongSinger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongRequest{  
    private String id;
    @NotBlank(message = "tên không được để trống")
    private String name;
    private String mp3;
    private String avatar;
    private String lyric;
    private Integer view;
    private List<SongSinger> songSingers = new ArrayList<>();
    private List<SongCategory> songCategorys = new ArrayList<>();

 }
