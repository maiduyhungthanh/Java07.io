package vn.techmaster.mp3.model;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "song_user")
@Table(name = "song_user")
@Data
@NoArgsConstructor
public class SongUser {
@Id
private String id;

@ManyToOne()
@JoinColumn(name = "song_id")
private Song song;

@ManyToOne()
@JoinColumn(name = "user_id")
private User user;

public SongUser (Song song,User user){
    this.id = UUID.randomUUID().toString();
    this.song = song;
    this.user = user;
}
}
