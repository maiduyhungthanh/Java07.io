package vn.techmaster.examjpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.examjpa.model.Category;


@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    
}
