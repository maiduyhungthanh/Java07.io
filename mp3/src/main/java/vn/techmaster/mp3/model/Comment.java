package vn.techmaster.mp3.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.*;

@Data
@Entity(name = "comment")
@Table(name="comment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    private String id;
    private String text;

      @ManyToOne()
      @JoinColumn(name = "post_id")
      private Post post;
      
      @ManyToOne()
      @JoinColumn(name = "user_id")
      private User user;

      public Comment (String text,Post post,User user){
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.post = post;
        this.user = user;
    }
}
