package vn.techmaster.mp3.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.techmaster.mp3.model.Singer;
import vn.techmaster.mp3.model.Song;
import vn.techmaster.mp3.service.FileService;
import vn.techmaster.mp3.service.SongSingerService;

@RestController
@RequestMapping("/api")
public class SongAPIController {

    @Autowired
    SongSingerService songSingerService;
    @Autowired
    private FileService fileService;

    @GetMapping("/song")
    public ResponseEntity<?> listSong() {
        List<Song> listSong = songSingerService.getAllSong();
        return ResponseEntity.ok(listSong);
    }

    @GetMapping("/singer")
    public ResponseEntity<?> listSinger() {
        Collection<Singer> listSinger = songSingerService.getAllSinger();
        return ResponseEntity.ok(listSinger);
    }

    @GetMapping("/singer/{id}")
    public ResponseEntity<?> singerById(@PathVariable String id) {
        Singer singer = songSingerService.getSingerById(id);
        return ResponseEntity.ok(singer);
    }

    @PostMapping("/singer")
    public ResponseEntity<?> createSinger(@RequestBody Singer request) {
        Singer singer = songSingerService.singerAdd(request);
        return ResponseEntity.ok(singer);
    }

    //chuyển file Ảnh sang String

    @PostMapping("/singer/files/{id}")
    public ResponseEntity<?> Uploadfile(@PathVariable String id, @ModelAttribute("file") MultipartFile file) {
        String path = fileService.uploadfile(id, file);
        return ResponseEntity.ok(path);
    }
    @GetMapping ("singer/files/{id}/{fileName}")
    public ResponseEntity<?> readFile (@PathVariable String id , @PathVariable String fileName) {
        byte[] bytes = fileService.readFile(id , fileName) ;
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }
    @PostMapping("/singer/image")
    public ResponseEntity<?> UploadImageSinger(@ModelAttribute("file") MultipartFile file) {
        String path = fileService.uploadImages(file);
        return ResponseEntity.ok(path);
    }
    @GetMapping ("singer/image/{fileName}")
    public ResponseEntity<?> readImageSinger (@PathVariable String fileName) {
        byte[] bytes = fileService.readImage(fileName) ;
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }
}
