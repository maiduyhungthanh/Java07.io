package vn.techmaster.mp3.model;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    public List<Singer> getSingers(){
      List<Singer> listSingers = new ArrayList<>();
      songSingers.stream().forEach(songSinger->{
        listSingers.add(songSinger.getSinger());
      });
      return listSingers;
    }

        // mot bai hat co nhieu the loai
        @JsonIgnore
        @OneToMany(mappedBy = "song")
        private List<SongCategory> songCategories = new ArrayList<>();
      
        
        @JsonGetter(value = "categorys")
        @Transient
        public List<Category> getCategorys(){
          List<Category> listCategorys = new ArrayList<>();
          songCategories.stream().forEach(songCategory->{
            listCategorys.add(songCategory.getCategory());
          });
          return listCategorys;
        }



}
