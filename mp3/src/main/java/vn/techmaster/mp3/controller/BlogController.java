package vn.techmaster.mp3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class BlogController {
        // blog
        @GetMapping("/blog")
        public String blog() {
            return "blog";
        }
         // blog chi tiet theo id
    @GetMapping("/blog|{id}")
    public String getAlbumById(@PathVariable String id) {
        return "blogById";
    }
    @GetMapping("/blogadd")
    public String getBlogAdd(){
        return "addBlog";
    }
}
