package vn.techmaster.mp3.model;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Table (name = "song")
@Entity(name = "song")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// @Indexed //Full Text Index

public class Song implements Serializable{
    @Id
    private String id;
    private String name;
    private String mp3;
    private String avatar;
    private String lyric;

    public Song(String name, String mp3, String avatar) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.mp3 = mp3;
        this.avatar = avatar;
    }
    // mot ca sy co nhieu bai hat
    @JsonIgnore
    @OneToMany(mappedBy = "song")
    private List<SongSinger> songSingers = new ArrayList<>();
  
    @JsonGetter(value = "singers")
    @Transient
    // public Map<String, Integer> getStudents() {
    //   Map<String, Integer> singerView = new HashMap<>();
    //     songSingers.stream().forEach( songSinger -> {
    //     singerView.put(songSinger.getSinger().getName(), songSinger.getView());
    //           }
    //   );
    //   return singerView;
    //         }
    public List<Singer> getSingers(){
      List<Singer> listSingers = new ArrayList<>();
      songSingers.stream().forEach(songSinger->{
        listSingers.add(songSinger.getSinger());
      });
      return listSingers;
    }


}
