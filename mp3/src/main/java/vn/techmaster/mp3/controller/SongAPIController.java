package vn.techmaster.mp3.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.mp3.model.Song;
import vn.techmaster.mp3.repository.SongRepo;
import vn.techmaster.mp3.service.SongService;

@RestController
@RequestMapping("/api")
public class SongAPIController {
    @Autowired
    SongRepo songRepo;
   
    SongService songService;

    @GetMapping("/song")
    public ResponseEntity<?> listSong(){
        List<Song> listSong = songRepo.findAll();
        return ResponseEntity.ok(listSong);
    }

    @GetMapping("/search")
    public ResponseEntity<?>searchSong(@RequestParam String search){
        Collection<Song> searchSong = songRepo.findAll()
        .stream()
        .filter(song->song.getName().toLowerCase().contains(search.toLowerCase())
        ||song.getSinger().toLowerCase().contains(search.toLowerCase()))
        .collect(Collectors.toList());
        return ResponseEntity.ok(searchSong);
    }
}
