package vn.techmaster.mp3.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Data
@Entity(name = "post")
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
  @Id
  String id;
  String title;
  // @Column(length=50000)
  String content;

  @ManyToOne()
  @JoinColumn(name = "user_id")
  private User user;

  public Post(String title, String content, User user) {
    this.id = UUID.randomUUID().toString();
    this.title = title;
    this.content = content;
    this.user = user;
  }

  // mot Post co nhieu user binh luận
  @JsonIgnore
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<Comment> comments = new ArrayList<>();

  @JsonGetter(value = "comments")
  @Transient
  public List<String> UserComments() {
    List<String> listComments = new ArrayList<>();
    comments.stream().forEach(comment->{
    listComments.add(comment.getText());
    });
    return listComments;
  }
  @JsonGetter(value = "users")
  @Transient
  public List<User> Users() {
    List<User> listUsers = new ArrayList<>();
    comments.stream().forEach(comment->{
      listUsers.add(comment.getUser());
    });
    return listUsers;
  }
}
