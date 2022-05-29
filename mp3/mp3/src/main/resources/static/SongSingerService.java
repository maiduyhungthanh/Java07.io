package vn.techmaster.mp3.service;

import java.util.List;

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

@Service
public class SongSingerService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    SingerRepo singerRepo;

    @Autowired
    SongRepo songRepo;



    @Transactional
    public void generateSongSinger() {
        Singer singer1 = new Singer("Duy Mạnh","image/hay_ve_day_ben_anh.jpg");
        Singer singer2 = new Singer("Min","image/dung_yeu_nua_em_met_roi.jpg");
        Singer singer3 = new Singer("Suni Hạ Linh", "image/khong_sao_ma_em_day_roi.jpg");


        Song song1 = new Song("lambada", "music/lambada.mp3","image/lambada.jpg");
        Song song2 = new Song("hay_ve_day_ben_anh", "music/hay_ve_day_ben_anh.mp3","image/hay_ve_day_ben_anh.jpg");
        Song song3 = new Song("dung_yeu_nua_em_met_roi", "music/dung_yeu_nua_em_met_roi.mp3","image/dung_yeu_nua_em_met_roi.jpg");
        Song song4 = new Song("khong_sao_ma_em_day_roi", "music/khong_sao_ma_em_day_roi.mp3","image/khong_sao_ma_em_day_roi.jpg");

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
    public List<Singer> getAllSinger(){
        return singerRepo.findAll();
    }
    
    // tat ca cac bai hat
    public List<Song> getAllSong(){
        return songRepo.findAll();
    }
}   
