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
public class HomeController {
    @Autowired SongSingerService songSingerService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }

    @GetMapping("/singer")
    public String singer() {
        return "singer";
    }

    @GetMapping("/addsinger")
    public String addSinger() {
        return "addSinger";
    }

    @GetMapping(value = "/singer|{id}")
    public String singerById(@PathVariable String id) {
        return "singerById";
    }
    @GetMapping(value = "/singer-edit|{id}")
    public String singerByIdEdit(@PathVariable String id,Model model) {
            Singer singer = songSingerService.SingerById(id).get();
            model.addAttribute("singer", singer);
        return "singerByIdEdit";
    }
    
}
