package vn.techmaster.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.user.dto.UserDto;
import vn.techmaster.user.request.UpdatePasswordRequest;
import vn.techmaster.user.request.UserRequest;
import vn.techmaster.user.request.UserRequestUpdate;
import vn.techmaster.user.response.FileReturn;
import vn.techmaster.user.service.FileService;
import vn.techmaster.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    @GetMapping("/users")
    public ResponseEntity<?>getUsers(){
        List<UserDto> userDtos = userService.getUsers();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/users/search")
    public ResponseEntity<?>searchUsers(@RequestParam String name){
        List<UserDto> userDtos = userService.searchUser(name);
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        UserDto userDto = userService.getUsersByID(id);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
         userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserRequest request){
        UserDto userDto = userService.createUser(request);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id , @RequestBody UserRequestUpdate request){
        UserDto userDto = userService.updateUser(id,request);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/user-change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable int id , @RequestBody UpdatePasswordRequest request){
        userService.updatePassword(id,request);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/users/forgot/{id}")
    public ResponseEntity<?> forgotPassword(@PathVariable int id){
        userService.forgotPassword(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/users/upload-file/{id}")
    public ResponseEntity<?> Uploadfile(@PathVariable int id, @ModelAttribute("file") MultipartFile file) {
        String path = fileService.uploadfile(id, file);
        return ResponseEntity.ok(path);
    }
    @GetMapping ("users/files/{id}/{fileName}")
    public ResponseEntity<?> readFile (@PathVariable int id , @PathVariable String fileName) {
        byte[] bytes = fileService.readFile(id , fileName) ;
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    @GetMapping ("users/files/{id}")
    public ResponseEntity<?> readFile (@PathVariable int id , @RequestParam int page)  {
        FileReturn files = fileService.getFiles(id,page);
        return ResponseEntity.ok(files);
    }
}
