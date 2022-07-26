package vn.techmaster.mp3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.mp3.model.SongUser;

@Repository
public interface SongUserRepo extends JpaRepository<SongUser,String> {
}
