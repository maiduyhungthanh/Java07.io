package vn.techmaster.mp3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.mp3.model.Singer;
import vn.techmaster.mp3.service.SongSingerService;

@Controller
@RequestMapping()
public class SongSingerController {
    @Autowired SongSingerService songSingerService;
    // trang chủ
    @GetMapping("/")
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
        return "singerById";
    }
    // update ca sỹ
    @GetMapping(value = "/singer-edit|{id}")
    public String singerByIdEdit(@PathVariable String id,Model model) {
            Singer singer = songSingerService.SingerById(id).get();
            model.addAttribute("singer", singer);
        return "singerByIdEdit";
    }
    // detail bài hát
    @GetMapping(value = "/song|{id}")
    public String songById(@PathVariable String id) {
        return "songById";
    }
    // update bài hát
    @GetMapping(value = "/song-edit|{id}")
    public String songByIdEdit(@PathVariable String id) {
        return "songByIdEdit";
    }
    // add bài hát
    @GetMapping("/addsong")
    public String addSong(){
        return "addSong";
    }
}
