package vn.techmaster.relation.service.onemany.bidirection;

import java.util.List;

import javax.transaction.Transactional;

import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.relation.model.onemany.bidirection.Comment;
import vn.techmaster.relation.model.onemany.bidirection.Post;
import vn.techmaster.relation.repository.onemany.bidirection.CommentRepository;
import vn.techmaster.relation.repository.onemany.bidirection.PostRepository;

@Service
public class PostService {
  @Autowired
  private PostRepository postRepo;

  @Autowired
  private CommentRepository commentRepo;

  @Transactional
  public void createPostAndComments() {
    Faker faker = new Faker();
    for (int i = 0; i < 5; i++) {
      Post post = new Post(faker.book().title(), faker.lorem().paragraph());
      for (int j = 0; j < 3; j++) {
        Comment comment = new Comment(faker.lorem().paragraph());
        post.addComment(comment);
      }
      postRepo.save(post);
    }
    postRepo.flush();
  }
  // public List<Post> updatePost(){
  //   Post post1 = postRepo.findAll().get(0);
  //   post1.get
  //   return postRepo.findAll();
  // }
  public List<Post> getAllPosts() {
    List<Post> result = postRepo.findAll();
    return result;
  }

}
