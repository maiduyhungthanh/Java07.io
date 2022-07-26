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
    private Integer view;

    public Song(String name, String mp3, String avatar, Integer view) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.mp3 = mp3;
        this.avatar = avatar;
        this.view = view;
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
       // mot bai hat co nhieu user yêu thích
       @JsonIgnore
       @OneToMany(mappedBy = "song",cascade = CascadeType.ALL)
       private List<SongUser> songUsers = new ArrayList<>();
     
       
       @JsonGetter(value = "users")
       @Transient
       public List<User> getUsers(){
         List<User> listUsers = new ArrayList<>();
         songUsers.stream().forEach(songUser->{
          listUsers.add(songUser.getUser());
         });
         return listUsers;
       }


}
