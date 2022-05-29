package vn.techmaster.mp3.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.mp3.dto.SingerDto;
import vn.techmaster.mp3.model.Singer;
import vn.techmaster.mp3.model.Song;
import vn.techmaster.mp3.request.SingerRequest;
import vn.techmaster.mp3.service.SongSingerService;

@RestController
@RequestMapping("/api")
public class SongAPIController {
   

    @Autowired
    SongSingerService songSingerService;


    @GetMapping("/song")
    public ResponseEntity<?> listSong(){
        List<Song> listSong = songSingerService.getAllSong();
        return ResponseEntity.ok(listSong);
    }

    @GetMapping("/singer")
    public ResponseEntity<?> listSinger(){
        Collection<Singer> listSinger = songSingerService.getAllSinger();
        return ResponseEntity.ok(listSinger);
    }

    @GetMapping("/singer/{id}")
    public ResponseEntity<?> singerById(@PathVariable String id){
    Singer singer = songSingerService.getSingerById(id);
        return ResponseEntity.ok(singer);
    }
    @PostMapping("/singer")
    public ResponseEntity<?> createSinger(@RequestBody SingerRequest request){
        SingerDto userDto = songSingerService.createSinger(request);
        return ResponseEntity.ok(userDto);
    }
}
