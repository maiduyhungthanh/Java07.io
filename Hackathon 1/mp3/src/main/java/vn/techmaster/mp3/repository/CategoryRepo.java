package vn.techmaster.mp3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.mp3.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category,String> {
}