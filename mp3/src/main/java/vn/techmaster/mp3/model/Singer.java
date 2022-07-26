package vn.techmaster.mp3.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "singer")
@Table(name="singer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Singer implements Serializable {
    @Id
    private String id;

    private String name;

    private String avatar;

    public Singer(String name, String avatar){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.avatar = avatar;
    }

    // Một Ca Sỹ có Nhiều Bài Hát 
    @OneToMany(mappedBy = "singer")
    @JsonIgnore
    private List<SongSinger> songSingers = new ArrayList<>();
  
    @JsonGetter(value = "songs")
    @Transient
    public Map<String, String> getSingers() {
      Map<String, String> songView = new HashMap<>();
      songSingers.stream().forEach( songSinger -> {
               songView.put(songSinger.getSong().getId(), songSinger.getSong().getName());
              }
      );
      return songView;
    }
}
