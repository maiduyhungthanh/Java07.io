package vn.techmaster.mp3.controller;

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

import io.swagger.v3.oas.annotations.Operation;
import vn.techmaster.mp3.model.User;
import vn.techmaster.mp3.request.UserRequest;
import vn.techmaster.mp3.service.AvatarUserService;
import vn.techmaster.mp3.service.UserServiceImpl;


@RestController
@RequestMapping("/api")
public class UserAPIController {

    @Autowired
    private AvatarUserService avatarUserService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    //-------------------------------------------------------------------------------------------------------------------
    //Thay Avatar User
    @Operation(summary = "Thay Avatar User")
    @PostMapping("/user/files/{id}")
    public ResponseEntity<?> UploadfileUser(@PathVariable String id, @ModelAttribute("file") MultipartFile file) {
        String path = avatarUserService.uploadfile(id, file);
        return ResponseEntity.ok(path);
    }
    // link Avatar User
    @Operation(summary = "link Avatar User")
    @GetMapping("/user/files/{id}/{fileName}")
    public ResponseEntity<?> readFileUser(@PathVariable String id, @PathVariable String fileName) {
        byte[] bytes = avatarUserService.readFile(id, fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }
    // sửa User
    @Operation(summary = "sửa Song")
    @PostMapping("/user-save")
    public ResponseEntity<?> updateSong(@RequestBody UserRequest request) {
        User user = userServiceImpl.updateUser(request);
        return ResponseEntity.ok(user);
    }
    // all User
    @Operation(summary = "all User")
    @GetMapping("/users")
    public ResponseEntity<?> allUser() {
        return ResponseEntity.ok(userServiceImpl.getAllUser());
    }
}
