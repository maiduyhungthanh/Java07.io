package vn.techmaster.mp3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.techmaster.mp3.model.Comment;
import vn.techmaster.mp3.request.CommentRequest;
import vn.techmaster.mp3.request.PostRequest;
import vn.techmaster.mp3.service.UserServiceImpl;

@RestController
@RequestMapping("/api")
public class BlogAPIController {
    @Autowired UserServiceImpl userServiceImpl;
       // tất cả các bài post
       @Operation(summary = "tất cả các bài Post")
       @GetMapping("/blog")
       public ResponseEntity<?> PostAll() {
           return ResponseEntity.ok(userServiceImpl.getPostAll());
       }
       // post theo id
       @Operation(summary = "Post theo id")
       @GetMapping("/blog/{id}")
       public ResponseEntity<?> singerById(@PathVariable String id) {
           return ResponseEntity.ok(userServiceImpl.getPostById(id));
       }
       //Comment trong bài Post
       @PostMapping("/comment")
       public ResponseEntity<?> CommentByUser(@RequestBody CommentRequest request) {
        Comment comment = userServiceImpl.getCommentNew(request);
        return ResponseEntity.ok(comment);
       }
       // đăng Post mới
       @PostMapping("/blog/add")
       public ResponseEntity<?> PostNew(@RequestBody PostRequest request) {
        return ResponseEntity.ok(userServiceImpl.getPostNew(request));
       }
       // xóa Post 
       @DeleteMapping(value = "/blog/delete/{id}")
       public ResponseEntity<?> DeletePost(@PathVariable String id) {
        return ResponseEntity.ok(userServiceImpl.RemovePost(id));
       }
}
