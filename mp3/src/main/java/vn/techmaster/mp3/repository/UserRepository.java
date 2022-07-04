package vn.techmaster.mp3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.mp3.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	User findByEmail(String email);
}
