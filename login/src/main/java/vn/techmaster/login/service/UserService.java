package vn.techmaster.login.service;

import java.util.Optional;

import vn.techmaster.login.model.User;
import vn.techmaster.login.request.RegisterRequest;

public interface UserService {
    public User login(String email,String password);
    public boolean logout (String email);

    public User addUser(String fullName,String email,String password);
    public User addUserThenAutoActivate(String fullName,String email,String password);
    public Boolean activateUser(String activation_code);

    public Boolean updatePassword(String email, String password);
    public Boolean updateEmail(String email, String newEmail );

    public Optional<User> findByEmail(String email);
    public User findById(String id);

    // public void sendValidate(RegisterRequest registerRequest);
    // public void checkValidate(String code);
}
