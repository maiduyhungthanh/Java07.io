package vn.techmaster.examjpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.examjpa.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    
}
