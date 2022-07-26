package vn.techmaster.mp3.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import vn.techmaster.mp3.model.User;
import vn.techmaster.mp3.request.UserRegistrationRequest;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationRequest UserRegistrationRequest);

}
