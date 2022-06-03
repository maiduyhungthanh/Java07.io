package vn.techmaster.examjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.examjpa.model.Post;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    
}
