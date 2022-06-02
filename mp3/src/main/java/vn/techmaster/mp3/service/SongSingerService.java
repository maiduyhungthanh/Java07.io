package vn.techmaster.mp3.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.mp3.dto.SingerDto;
import vn.techmaster.mp3.exception.NotFoundException;
import vn.techmaster.mp3.mapper.SingerMapper;
import vn.techmaster.mp3.model.Singer;
import vn.techmaster.mp3.model.Song;
import vn.techmaster.mp3.model.SongSinger;
import vn.techmaster.mp3.repository.SingerRepo;
import vn.techmaster.mp3.repository.SongRepo;
import vn.techmaster.mp3.repository.SongSingerRepo;
import vn.techmaster.mp3.request.SingerRequestUpdate;

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

        Song song1 = new Song("lambada", "../music/lambada.mp3", "images/lambada.jpg");
        Song song2 = new Song("hay_ve_day_ben_anh", "../music/hay_ve_day_ben_anh.mp3", "images/hay_ve_day_ben_anh.jpg");
        Song song3 = new Song("dung_yeu_nua_em_met_roi", "../music/dung_yeu_nua_em_met_roi.mp3",
                "images/dung_yeu_nua_em_met_roi.jpg");
        Song song4 = new Song("khong_sao_ma_em_day_roi", "../music/khong_sao_ma_em_day_roi.mp3",
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

    public Singer getSingerById(String id) {
        return singerRepo.getById(id);
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
    @Transactional
    public void deleteSinger(String id) {
        singerRepo.deleteById(id);
        }
           // edit singer
    public Singer updateSinger(Singer singer) {
        singerRepo.save(singer);
        List<SongSinger> songsinger =songSingerRepo.findAll().stream().filter(s->s.getSinger().getId()==singer.getId()).collect(Collectors.toList());
        for (SongSinger s : songsinger) {
            em.persist(s);
        }
        em.flush();
        return singer;
    }
    public SingerDto editSinger(String id, SingerRequestUpdate request) {
        Optional<Singer> singerOptional = singerRepo.findById(id) ;
        if(singerOptional.isEmpty()){
            throw new NotFoundException("user id " +id +" not found");
        }
        Singer singer = singerOptional.get();
        singer.setName(request.getName());

        return SingerMapper.toSingerDto(singer);
    }
}
