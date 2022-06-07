package vn.techmaster.demobank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.demobank.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    
}
