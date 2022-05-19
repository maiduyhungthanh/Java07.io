package vn.techmaster.mp3.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/song")
public class SongController {


    @GetMapping()
    public String homeSong() {
      
        return "song";
    }


}
