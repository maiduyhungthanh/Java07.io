package vn.techmaster.mp3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.mp3.model.Singer;
import vn.techmaster.mp3.model.Song;
import vn.techmaster.mp3.model.SongSinger;
import vn.techmaster.mp3.repository.SingerRepo;
import vn.techmaster.mp3.repository.SongRepo;
import vn.techmaster.mp3.repository.SongSingerRepo;

@Service
public class SongSingerService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    SingerRepo singerRepo;

    @Autowired
    SongRepo songRepo;

    @Autowired
    SongSingerRepo songSingerRepo;

    @Transactional
    public void generateSongSinger() {
        Singer singer1 = new Singer("Duy Mạnh", "images/hay_ve_day_ben_anh.jpg");
        Singer singer2 = new Singer("Min", "images/dung_yeu_nua_em_met_roi.jpg");
        Singer singer3 = new Singer("Suni Hạ Linh", "images/khong_sao_ma_em_day_roi.jpg");

        Song song1 = new Song("lambada", "music/lambada.mp3", "images/lambada.jpg");
        Song song2 = new Song("Hãy về đây bên anh", "music/hay_ve_day_ben_anh.mp3", "images/hay_ve_day_ben_anh.jpg");
        Song song3 = new Song("Đừng yêu nữa em mệt rồi", "music/dung_yeu_nua_em_met_roi.mp3",
                "images/dung_yeu_nua_em_met_roi.jpg");
        Song song4 = new Song("Không sao ma em đây rồi", "music/khong_sao_ma_em_day_roi.mp3",
                "images/khong_sao_ma_em_day_roi.jpg");

        SongSinger duymanh1 = new SongSinger(song1, singer1, 100);
        SongSinger duymanh2 = new SongSinger(song2, singer1, 200);
        SongSinger min1 = new SongSinger(song1, singer2, 100);
        SongSinger min2 = new SongSinger(song3, singer2, 500);
        SongSinger suni1 = new SongSinger(song4, singer3, 1000);

        em.persist(singer1);
        em.persist(singer2);
        em.persist(singer3);

        em.persist(song1);
        em.persist(song2);
        em.persist(song3);
        em.persist(song4);

        em.persist(duymanh1);
        em.persist(duymanh2);
        em.persist(min1);
        em.persist(min2);
        em.persist(suni1);

        em.flush();

    }

    // tat ca cac ca sy
    public List<Singer> getAllSinger() {
        return singerRepo.findAll();
    }

    // ca sy theo id
    public Optional<Singer> SingerById(String id) {
        return singerRepo.findById(id);
    }

    // tat ca cac bai hat
    public List<Song> getAllSong() {
        return songRepo.findAll();
    }

    // add singer
    public Singer singerAdd(Singer singer) {
        String id = UUID.randomUUID().toString();
        singer.setId(id);
        singerRepo.save(singer);
        return singer;
    }

    // delete singer
    public void deleteSinger(String id) {
        singerRepo.deleteById(id);
    }

    // edit singer
    public Singer updateSinger(Singer singer) {
        singerRepo.save(singer);
        List<SongSinger> songsinger = songSingerRepo.findAll().stream()
                .filter(s -> s.getSinger().getId() == singer.getId()).collect(Collectors.toList());
        for (SongSinger s : songsinger) {
            em.persist(s);
        }
        em.flush();
        return singer;
    }

    // lay danh sach nhac theo ca sy
    public List<Song> getSongBySinger(String id) {
        Optional<Singer> singer = singerRepo.findById(id);
        List<String> ids = new ArrayList<>();
        for (String id_song : singer.get().getStudents().keySet()) {
            ids.add(id_song);
        }
        List<Song> songs = songRepo.findAll().stream().filter(s -> ids.contains(s.getId()))
                .collect(Collectors.toList());
        return songs;
    }

    // bài hát theo id
    public Optional<Song> SongById(String id) {
        return songRepo.findById(id);
    }

    // xóa bài hát
    public void deleteSong(String id) {
        songRepo.deleteById(id);
    }

    // sửa bài hát
    public Song updateSong(Song song) {
        songRepo.save(song);
        List<SongSinger> songsinger = songSingerRepo.findAll().stream()
                .filter(s -> s.getSinger().getId() == song.getId()).collect(Collectors.toList());
        for (SongSinger s : songsinger) {
            em.persist(s);
        }
        em.flush();
        return song;
    }

    // thêm bài hát
    public Song songAdd(Song song) {
        List<Singer> singers = new ArrayList<>();
        String id = UUID.randomUUID().toString();
        song.setId(id);
        songRepo.save(song);
        String[] id_singer = song.getLyric().split("/");
        for (int i = 0; i < id_singer.length; i++) {
            Optional<Singer> singerOptional = singerRepo.findById(id_singer[i]);
            if (!singerOptional.isEmpty()) {
                singers.add(singerOptional.get());
            }
        }
        for (Singer singer : singers) {
            SongSinger aaa = new SongSinger(song, singer, 100);
            songSingerRepo.save(aaa);
        }
        song.setLyric(null);
        songRepo.save(song);
        return song;
    }

}
