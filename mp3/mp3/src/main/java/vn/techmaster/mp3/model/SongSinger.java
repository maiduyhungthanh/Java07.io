package vn.techmaster.mp3.model;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "song_singer")
@Table(name = "song_singer")
@Data
@NoArgsConstructor
public class SongSinger {
@Id
private String id;

@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "song_id")
private Song song;

@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "singer_id")
private Singer singer;

private Integer view;

public SongSinger (Song song,Singer singer,Integer view){
    this.id = UUID.randomUUID().toString();
    this.song = song;
    this.singer = singer;
    this.view = view;
}

}
