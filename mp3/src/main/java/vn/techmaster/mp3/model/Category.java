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
@Entity(name = "category")
@Table(name="category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category implements Serializable {
    @Id
    private String id;

    private String name;

    private String avatar;

    public Category(String name, String avatar){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.avatar = avatar;
    }

    // Một Thể loại có Nhiều Bài Hát 
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<SongCategory> songCategorys = new ArrayList<>();
  
    @JsonGetter(value = "songs")
    @Transient
    public Map<String, String> getCategorys() {
      Map<String, String> songs = new HashMap<>();
      songCategorys.stream().forEach( songCategory -> {
               songs.put(songCategory.getSong().getId(), songCategory.getSong().getName());
              }
      );
      return songs;
    }
}
