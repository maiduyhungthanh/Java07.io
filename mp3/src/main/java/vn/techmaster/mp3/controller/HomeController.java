package vn.techmaster.mp3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping()
    public String home(){
        return "home";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/contacts")
    public String contacts(){
        return "contacts";
    }
    @GetMapping("/singer")
    public String singer(){
        return "singer";
    }
    @GetMapping("/addsinger")
    public String addSinger(){
        return "addSinger";
    }
}
