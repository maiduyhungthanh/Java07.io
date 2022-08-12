package vn.techmaster.mp3.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.mp3.model.Comment;
@Repository
public interface CommentRepo extends JpaRepository<Comment,String>{
    
}
