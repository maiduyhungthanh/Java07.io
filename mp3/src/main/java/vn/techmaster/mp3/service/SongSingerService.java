package vn.techmaster.mp3.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.mp3.exception.BadRequestException;
import vn.techmaster.mp3.model.Category;
import vn.techmaster.mp3.model.Singer;
import vn.techmaster.mp3.model.Song;
import vn.techmaster.mp3.model.SongCategory;
import vn.techmaster.mp3.model.SongSinger;
import vn.techmaster.mp3.repository.CategoryRepo;
import vn.techmaster.mp3.repository.SingerRepo;
import vn.techmaster.mp3.repository.SongCategoryRepo;
import vn.techmaster.mp3.repository.SongRepo;
import vn.techmaster.mp3.repository.SongSingerRepo;
import vn.techmaster.mp3.request.CategoryRequest;
import vn.techmaster.mp3.request.SingerRequest;
import vn.techmaster.mp3.request.SongRequest;

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
    @Autowired
    SongCategoryRepo songCategoryRepo;
    @Autowired
    CategoryRepo categoryRepo;

    @Transactional
    public void generateSongSinger() {
        Singer singer1 = new Singer("Duy Mạnh", "images/hay_ve_day_ben_anh.jpg");
        Singer singer2 = new Singer("Min", "images/dung_yeu_nua_em_met_roi.jpg");
        Singer singer3 = new Singer("Suni Hạ Linh",
        "images/khong_sao_ma_em_day_roi.jpg");
        Singer singer4 = new Singer("Hà Anh Tuấn",
        "images/khong_sao_ma_em_day_roi.jpg");
        Singer singer5 = new Singer("Phương Linh",
        "images/khong_sao_ma_em_day_roi.jpg");

        Song song1 = new Song("Cơn mưa tình yêu", "music/con_mua_tinh_yeu.mp3","images/lambada.jpg",10);
        Song song2 = new Song("Hãy về đây bên anh", "music/hay_ve_day_ben_anh.mp3",
        "images/hay_ve_day_ben_anh.jpg",10);
        Song song3 = new Song("Đừng yêu nữa em mệt rồi",
        "music/dung_yeu_nua_em_met_roi.mp3",
        "images/dung_yeu_nua_em_met_roi.jpg",10);
        Song song4 = new Song("Không sao ma em đây rồi",
        "music/khong_sao_ma_em_day_roi.mp3",
        "images/khong_sao_ma_em_day_roi.jpg",10);
        Song song5 = new Song("Qua đêm nay", "music/qua_dem_nay.mp3",
        "images/lambada.jpg",10);

        SongSinger haanhtuan1 = new SongSinger(song1, singer4);
        SongSinger phuonglinh1 = new SongSinger(song1, singer5);
        SongSinger haanhtuan2 = new SongSinger(song5, singer4);
        SongSinger phuonglinh2 = new SongSinger(song5, singer5);
        SongSinger duymanh2 = new SongSinger(song2, singer1);
        SongSinger min1 = new SongSinger(song3, singer2);
        SongSinger suni1 = new SongSinger(song4, singer3);

        Category category1 = new Category("Nhạc Trẻ",
        "/images/khong_sao_ma_em_day_roi.jpg");
        Category category2 = new Category("Nhạc 8x-9x",
        "/images/hay_ve_day_ben_anh.jpg");

        SongCategory _1_8x9x = new SongCategory(song1, category2);
        SongCategory _2_8x9x = new SongCategory(song2, category2);
        SongCategory tre1 = new SongCategory(song3, category1);
        SongCategory tre2 = new SongCategory(song4, category1);
        SongCategory tre3 = new SongCategory(song5, category1);

        em.persist(singer1);
        em.persist(singer2);
        em.persist(singer3);
        em.persist(singer4);
        em.persist(singer5);
        em.persist(song1);
        em.persist(song2);
        em.persist(song3);
        em.persist(song4);
        em.persist(song5);
        em.persist(_1_8x9x);
        em.persist(_2_8x9x);
        em.persist(tre1);
        em.persist(tre2);
        em.persist(tre3);
        em.persist(haanhtuan1);
        em.persist(duymanh2);
        em.persist(min1);
        em.persist(phuonglinh1);
        em.persist(suni1);
        em.persist(haanhtuan2);
        em.persist(phuonglinh2);

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
    public Singer singerAdd(SingerRequest singer) {
        String id = UUID.randomUUID().toString();
        singer.setId(id);
        Singer singerNew = new Singer(singer.getId(), singer.getName(), singer.getAvatar(), singer.getSongSingers());
        if (singer.getName() == "") {
            throw new BadRequestException("Tên Ca sỹ không được để trống");
        }
        singerRepo.save(singerNew);
        return singerNew;
    }

    // delete singer - Xoa Ca Sy thi xoa luon cac bai hat cua Ca sy day
    public void deleteSinger(String id) {
        singerRepo.deleteById(id);
        for (Song s : songRepo.findAll()) {
            if (s.getSingers().size() == 0) {
                songRepo.delete(s);
            }
        }
    }

    // edit singer
    public Singer updateSinger(SingerRequest singerUpdate) {
        Singer singer = new Singer(singerUpdate.getId(), singerUpdate.getName(), singerUpdate.getAvatar(),
                singerUpdate.getSongSingers());
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
        for (String id_song : singer.get().getSingers().keySet()) {
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
    public Song updateSong(SongRequest songUpdate) {
        Song song = new Song(songUpdate.getId(), songUpdate.getName(), songUpdate.getMp3(), songUpdate.getAvatar(),
                songUpdate.getLyric(),songRepo.findById(songUpdate.getId()).get().getView(), songUpdate.getSongSingers(), songUpdate.getSongCategorys(), null);
                List<Singer> singers = new ArrayList<>();
                List<Category> categories = new ArrayList<>();
        songRepo.save(song);
        String[] id_singer = song.getLyric().split("/");
        for (int i = 0; i < id_singer.length; i++) {
            Optional<Singer> singerOptional = singerRepo.findById(id_singer[i]);
            if (!singerOptional.isEmpty()) {
                singers.add(singerOptional.get());
            } else {
                Optional<Category> categoryOptional = categoryRepo.findById(id_singer[i]);
                if (!categoryOptional.isEmpty()) {
                    categories.add(categoryOptional.get());
                }
            }
        }
        for (SongSinger songSinger : songSingerRepo.findAll()) {
            if(songSinger.getSong().getId().equals(song.getId())){
                songSingerRepo.delete(songSinger);
            }
        }
        for (SongCategory songCategory : songCategoryRepo.findAll()) {
            if(songCategory.getSong().getId().equals(song.getId())){
                songCategoryRepo.delete(songCategory);
            }
        }
        for (Singer singer : singers) {
            SongSinger songSinger = new SongSinger(song, singer);
            songSingerRepo.save(songSinger);
        }
        for (Category category : categories) {
            SongCategory sc = new SongCategory(song, category);
            songCategoryRepo.save(sc);
        }
        song.setLyric(null);
        songRepo.save(song);
        return song;
    }

    // thêm bài hát
    public Song songAdd(SongRequest songNew) {
        String id = UUID.randomUUID().toString();
        songNew.setId(id);
        Song song = new Song(songNew.getId(), songNew.getName(), songNew.getMp3(), songNew.getAvatar(),
                songNew.getLyric(),5, songNew.getSongSingers(), songNew.getSongCategorys(), null);
        List<Singer> singers = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        songRepo.save(song);
        String[] id_singer = song.getLyric().split("/");
        for (int i = 0; i < id_singer.length; i++) {
            Optional<Singer> singerOptional = singerRepo.findById(id_singer[i]);
            if (!singerOptional.isEmpty()) {
                singers.add(singerOptional.get());
            } else {
                Optional<Category> categoryOptional = categoryRepo.findById(id_singer[i]);
                if (!categoryOptional.isEmpty()) {
                    // categoryOptional.get().setAvatar(song.getAvatar());
                    categories.add(categoryOptional.get());
                }
            }
        }
        for (Singer singer : singers) {
            SongSinger songSinger = new SongSinger(song, singer);
            songSingerRepo.save(songSinger);
        }
        for (Category category : categories) {
            SongCategory sc = new SongCategory(song, category);
            songCategoryRepo.save(sc);
        }
        song.setLyric(null);
        songRepo.save(song);
        return song;
    }

    // tim kiem bai hat theo ten ca sy hoac bai hat
    public List<Song> songByKeyWord(String keywork) {
        List<Song> songs = new ArrayList<>();
        for (Song song : songRepo.findAll()) {
            if (song.getName().toLowerCase().contains(keywork.toLowerCase())) {
                songs.add(song);
            } else {
                for (Singer singer : song.getSingers()) {
                    if (singer.getName().toLowerCase().contains(keywork.toLowerCase())) {
                        songs.add(song);
                    }
                }
            }
        }
        return songs;
    }

    // danh sách thể loại
    public List<Category> getCategoryAll() {
        return categoryRepo.findAll();
    }

    // the loai chi tieet
    public Category getCategoryById(String id) {
        return categoryRepo.findById(id).get();
    }

    // lay danh sach bài hát theo thể loại
    public List<Song> getSongByCategory(String id) {
        Optional<Category> category = categoryRepo.findById(id);
        List<String> ids = new ArrayList<>();
        for (String id_song : category.get().getCategorys().keySet()) {
            ids.add(id_song);
        }
        List<Song> songs = songRepo.findAll().stream().filter(s -> ids.contains(s.getId()))
                .collect(Collectors.toList());
        return songs;
    }

    // them the loai
    public Category categoryAdd(CategoryRequest category) {
        String id = UUID.randomUUID().toString();
        String avatar = "images/lambada.jpg";
        category.setId(id);
        category.setAvatar(avatar);
        Category categoryNew = new Category(category.getId(), category.getName(), category.getAvatar(),
                category.getSongCategories());
        if (categoryNew.getName() == "") {
            throw new BadRequestException("Tên Album không được để trống");
        }
        categoryRepo.save(categoryNew);
        return categoryNew;
    }

    // delete category - Xoa thể loại thi ko xoa luon cac bai hat
    public void deleteCategory(String id) {
        categoryRepo.deleteById(id);
    }

    // edit Category
    public Category updateCategory(CategoryRequest categoryEdit) {
        Category category = new Category(categoryEdit.getId(), categoryEdit.getName(), categoryEdit.getAvatar(),
                categoryEdit.getSongCategories());
        categoryRepo.save(category);
        List<SongCategory> songCategories = songCategoryRepo.findAll().stream()
                .filter(s -> s.getCategory().getId() == category.getId()).collect(Collectors.toList());
        for (SongCategory s : songCategories) {
            em.persist(s);
        }
        em.flush();
        return category;
    }
    // them luot View
    public Song updateView (String id){
        Song song = songRepo.findById(id).get();
        song.setView(song.getView()+1);
        songRepo.save(song);
        return song;
    }
    // Bang xep hang top 10 bai hat view nhieu nhat
    public List<Song> topSong(){
        return songRepo.findAll().stream().sorted(Comparator.comparing(Song::getView).reversed()).limit(5).toList();
    }
}
