package vn.techmaster.mp3.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import vn.techmaster.mp3.model.Category;
import vn.techmaster.mp3.model.Singer;
import vn.techmaster.mp3.model.Song;
import vn.techmaster.mp3.request.CategoryRequest;
import vn.techmaster.mp3.request.SingerRequest;
import vn.techmaster.mp3.request.SongRequest;
import vn.techmaster.mp3.service.FileCategoryService;
import vn.techmaster.mp3.service.FileMp3Service;
import vn.techmaster.mp3.service.FileSingerService;
import vn.techmaster.mp3.service.FileSongService;
import vn.techmaster.mp3.service.SongSingerService;

@RestController
@RequestMapping("/api")
public class SongSingerAPIController {

    @Autowired
    SongSingerService songSingerService;
    @Autowired
    private FileSingerService fileSingerService;
    @Autowired
    private FileMp3Service fileMp3Service;
    @Autowired
    private FileSongService fileSongService;
    @Autowired
    private FileCategoryService fileCategoryService;

    // all ca sỹ
    @Operation(summary = "tất cả ca sỹ")
    @GetMapping(value = "/singer")
    public ResponseEntity<?> listSinger() {
        Collection<Singer> listSinger = songSingerService.getAllSinger();
        return ResponseEntity.ok(listSinger);
    }

    // ca sỹ theo id
    @Operation(summary = "Ca sỹ theo id")
    @GetMapping("/singer/{id}")
    public ResponseEntity<?> singerById(@PathVariable String id) {
        Optional<Singer> singer = songSingerService.SingerById(id);
        return ResponseEntity.ok(singer);
    }

    // add ca sỹ
    @Operation(summary = "thêm ca sỹ")
    @PostMapping("/singer")
    public ResponseEntity<?> addSinger(@RequestBody SingerRequest request) {
        Singer singer = songSingerService.singerAdd(request);
        return ResponseEntity.ok(singer);
    }

    // update ca sỹ
    @Operation(summary = "Sửa ca sỹ")
    @PostMapping("/singer-save")
    public ResponseEntity<?> updateSinger(@RequestBody SingerRequest request) {
        Singer singer = songSingerService.updateSinger(request);
        return ResponseEntity.ok(singer);
    }

    // lưu ảnh update của ca sỹ
    @Operation(summary = "Sửa ảnh ca sỹ")
    @PostMapping("/singer/files/{id}")
    public ResponseEntity<?> Uploadfile(@PathVariable String id, @ModelAttribute("file") MultipartFile file) {
        String path = fileSingerService.uploadfile(id, file);
        return ResponseEntity.ok(path);
    }

    // link ảnh update ca sỹ
    @Operation(summary = "link ảnh cho ca sỹ vừa mới đc sửa")
    @GetMapping("/singer/files/{id}/{fileName}")
    public ResponseEntity<?> readFile(@PathVariable String id, @PathVariable String fileName) {
        byte[] bytes = fileSingerService.readFile(id, fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    // lưu ảnh add ca sỹ
    @PostMapping("/singer/image")
    @Operation(summary = "Lưu ảnh ca sỹ mới")
    public ResponseEntity<?> UploadImageSinger(@ModelAttribute("file") MultipartFile file) {
        String path = fileSingerService.uploadImages(file);
        return ResponseEntity.ok(path);
    }

    // link ảnh add ca sỹ
    @Operation(summary = "link ảnh cho ca sỹ vừa thêm mới")
    @GetMapping("/singer/image/{fileName}")
    public ResponseEntity<?> readImageSinger(@PathVariable String fileName) {
        byte[] bytes = fileSingerService.readImage(fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    // xóa ca sỹ
    @Operation(summary = "xóa ca sỹ")
    @DeleteMapping(value = "/singer/delete/{id}")
    public ResponseEntity<?> deleteSingerByID(@PathVariable String id) {
        songSingerService.deleteSinger(id);
        return ResponseEntity.ok(songSingerService.getAllSinger());
    }

    // list bài hát theo từng ca sỹ
    @Operation(summary = "list bài hát theo từng ca sỹ")
    @GetMapping("/singer/song/{id}")
    public ResponseEntity<?> SongBySinger(@PathVariable String id) {
        List<Song> songs = songSingerService.getSongBySinger(id);
        return ResponseEntity.ok(songs);
    }

    // ....................................................................
    // all bài hát
    @Operation(summary = "all bài hát")
    @GetMapping("/song")
    public ResponseEntity<?> listSong() {
        List<Song> listSong = songSingerService.getAllSong();
        return ResponseEntity.ok(listSong);
    }

    // bài hát theo id
    @Operation(summary = "bài hát theo id")
    @GetMapping("/song/{id}")
    public ResponseEntity<?> songById(@PathVariable String id) {
        Optional<Song> song = songSingerService.SongById(id);
        return ResponseEntity.ok(song);
    }

    // xóa bài hát
    @Operation(summary = "xóa bài hát")
    @DeleteMapping(value = "/song/delete/{id}")
    public ResponseEntity<?> deleteSongByID(@PathVariable String id) {
        songSingerService.deleteSong(id);
        return ResponseEntity.ok(songSingerService.getAllSinger());
    }

    // lưu file mp3
    @Operation(summary = "lưu file mp3")
    @PostMapping("/song/mp3")
    public ResponseEntity<?> UploadFileMp3(@ModelAttribute("file") MultipartFile file) {
        String path = fileMp3Service.addMp3(file);
        return ResponseEntity.ok(path);
    }

    // link mp3
    @Operation(summary = "link mp3")
    @GetMapping("/song/mp3/{fileName}")
    public ResponseEntity<?> readMp3(@PathVariable String fileName) {
        byte[] bytes = fileMp3Service.readMp3(fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    // lưu ảnh add bài hát
    @Operation(summary = "lưu ảnh add bài hát")
    @PostMapping("/song/image")
    public ResponseEntity<?> UploadImageSong(@ModelAttribute("file") MultipartFile file) {
        String path = fileSongService.uploadImages(file);
        return ResponseEntity.ok(path);
    }

    // link ảnh add bài hát
    @Operation(summary = "link ảnh add bài hát")
    @GetMapping("/song/image/{fileName}")
    public ResponseEntity<?> readImageSong(@PathVariable String fileName) {
        byte[] bytes = fileSongService.readImage(fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    // add song
    @Operation(summary = "add bài hát")
    @PostMapping("/song")
    public ResponseEntity<?> addSong(@RequestBody SongRequest request) {
        Song song = songSingerService.songAdd(request);
        return ResponseEntity.ok(song);
    }

    // lưu ảnh update của bai hat
    @Operation(summary = "lưu ảnh update của bài hát")
    @PostMapping("/song/files/{id}")
    public ResponseEntity<?> UploadfileSong(@PathVariable String id, @ModelAttribute("file") MultipartFile file) {
        String path = fileSongService.uploadfile(id, file);
        return ResponseEntity.ok(path);
    }

    // link ảnh update bài hát
    @Operation(summary = "lưu ảnh update của bài hát")
    @GetMapping("/song/files/{id}/{fileName}")
    public ResponseEntity<?> readFileSong(@PathVariable String id, @PathVariable String fileName) {
        byte[] bytes = fileSongService.readFile(id, fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    // sửa Song
    @Operation(summary = "sửa Song")
    @PostMapping("/song-save")
    public ResponseEntity<?> updateSong(@RequestBody SongRequest request) {
        Song song = songSingerService.updateSong(request);
        return ResponseEntity.ok(song);
    }

    // tim kiem theo Keywork
    @Operation(summary = "tìm kiếm theo Keywork")
    @GetMapping("/songByKeyword")
    public ResponseEntity<?> listSongKeyWord(@RequestParam String keyword) {
        List<Song> listSong = songSingerService.songByKeyWord(keyword);
        return ResponseEntity.ok(listSong);
    }

    // -------------------------------------------------------------------------------------------------------------------
    // List the loai
    @Operation(summary = "danh sách Category")
    @GetMapping("/category")
    public ResponseEntity<?> listAlbum() {
        List<Category> listCategory = songSingerService.getCategoryAll();
        return ResponseEntity.ok(listCategory);
    }

    // Category chi tiet
    @Operation(summary = "Category detail")
    @GetMapping("/category/{id}")
    public ResponseEntity<?> AlbumById(@PathVariable String id) {
        Category category = songSingerService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    // List Song theo thế loại
    @Operation(summary = "danh sách bài hát theo thể loại")
    @GetMapping("/category/song/{id}")
    public ResponseEntity<?> listSongbyCategory(@PathVariable String id) {
        List<Song> songs = songSingerService.getSongByCategory(id);
        return ResponseEntity.ok(songs);
    }

    // Add Category
    @Operation(summary = "add thể loại")
    @PostMapping("/category")
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest request) {
        Category category = songSingerService.categoryAdd(request);
        return ResponseEntity.ok(category);
    }

    // Delete Category
    @Operation(summary = "xóa category")
    @DeleteMapping(value = "/category/delete/{id}")
    public ResponseEntity<?> deleteCategoryByID(@PathVariable String id) {
        songSingerService.deleteCategory(id);
        return ResponseEntity.ok(songSingerService.getCategoryAll());
    }

    // Edit Category
    @Operation(summary = "Sửa category")
    @PostMapping("/category-save")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest request) {
        Category category = songSingerService.updateCategory(request);
        return ResponseEntity.ok(category);
    }

    // lưu ảnh update của album
    @Operation(summary = "Sửa ảnh album")
    @PostMapping("/category/files/{id}")
    public ResponseEntity<?> UploadfileCategory(@PathVariable String id, @ModelAttribute("file") MultipartFile file) {
        String path = fileCategoryService.uploadfile(id, file);
        return ResponseEntity.ok(path);
    }

    // link ảnh update album
    @Operation(summary = "link ảnh cho album vừa mới đc sửa")
    @GetMapping("/category/files/{id}/{fileName}")
    public ResponseEntity<?> readFileCategory(@PathVariable String id, @PathVariable String fileName) {
        byte[] bytes = fileCategoryService.readFile(id, fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }
    //top Song
    @GetMapping("/topsong")
    public ResponseEntity<?> topSong(){
        return ResponseEntity.ok(songSingerService.topSong());
    }
}
