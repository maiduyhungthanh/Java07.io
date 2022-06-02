package vn.techmaster.mp3.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.mp3.model.SongSinger;

@Repository
public interface SongSingerRepo extends JpaRepository<SongSinger,String> {
}
