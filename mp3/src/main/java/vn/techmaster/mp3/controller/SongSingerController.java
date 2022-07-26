package vn.techmaster.mp3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.techmaster.mp3.exception.NotFoundException;
import vn.techmaster.mp3.repository.CategoryRepo;
import vn.techmaster.mp3.repository.SingerRepo;
import vn.techmaster.mp3.service.EmailService;
import vn.techmaster.mp3.service.SongSingerService;

@Controller
@RequestMapping()
public class SongSingerController {
    @Autowired
    SongSingerService songSingerService;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    SingerRepo singerRepo;
    @Autowired
    EmailService emailService;

    // trang chủ
    @GetMapping(value = { "/", "/none" })
    public String index() {
        return "index";
    }

    // đóng góp ý kiến
    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }

    // danh sách ca sỹ
    @GetMapping("/singer")
    public String singer() {
        return "singer";
    }

    // add ca sỹ
    @GetMapping("/addsinger")
    public String addSinger() {
        return "addSinger";
    }

    // detail ca sỹ
    @GetMapping(value = "/singer|{id}")
    public String singerById(@PathVariable String id) {
        if(singerRepo.findById(id).isEmpty()){
            throw new NotFoundException("Địa chỉ không tồn tại");
        }
        return "singerById";
    }

    // update ca sỹ
    @GetMapping(value = "/singer-edit|{id}")
    public String singerByIdEdit(@PathVariable String id) {
        if(singerRepo.findById(id).isEmpty()){
            throw new NotFoundException("Địa chỉ không tồn tại");
        }
        return "singerByIdEdit";
    }

    // detail bài hát
    @GetMapping(value = "/song|{id}")
    public String songById(@PathVariable String id) {
        songSingerService.updateView(id);
        return "songById";
    }

    // update bài hát
    @GetMapping(value = "/song-edit|{id}")
    public String songByIdEdit(@PathVariable String id) {
        return "songByIdEdit";
    }

    // add bài hát
    @GetMapping("/addsong")
    public String addSong() {
        return "addSong";
    }

    // tìm kiếm bài hát theo keyword
    @GetMapping("/song")
    public String songByKeyword(@RequestParam String keyword) {
        return "songByKeyword";
    }

    // blog
    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

    // album
    @GetMapping("/album")
    public String category() {
        return "album";
    }

    // album chi tiet
    @GetMapping("/album|{id}")
    public String getAlbumById(@PathVariable String id) {
        return "albumById";
    }

    // add album
    @GetMapping("/addalbum")
    public String getAddAlbum() {
        return "addAlbum";
    }

    // update album
    @GetMapping(value = "/album-edit|{id}")
    public String albumByIdEdit(@PathVariable String id) {
        if(categoryRepo.findById(id).isEmpty()){
            throw new NotFoundException("Đường truyền không chính xác");
        }
        return "albumByIdEdit";
    }

}
