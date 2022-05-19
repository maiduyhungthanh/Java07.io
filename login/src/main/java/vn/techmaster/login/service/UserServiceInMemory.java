package vn.techmaster.login.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import vn.techmaster.login.exception.UserException;
import vn.techmaster.login.hash.Hashing;
import vn.techmaster.login.model.State;
import vn.techmaster.login.model.User;
import vn.techmaster.login.repository.UserRepo;
import vn.techmaster.login.request.RegisterRequest;

@Service
public class UserServiceInMemory implements UserService {
    private UserRepo userRepo;
    private Hashing hashing;

    // @Autowired
    // private EmailService emailService;
  
    private ConcurrentHashMap<String, String> activateds = new ConcurrentHashMap<>();

    @Override
    public Boolean activateUser(String activation_code) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User addUser(String fullName, String email, String password) {
        return userRepo.addUser(fullName, email, hashing.hashPassword(password));
    }

    @Override
    public User addUserThenAutoActivate(String fullName, String email, String password) {
        return userRepo.addUserThenActivate(fullName, email, password);
    }

    @Override
    public User login(String email, String password) {
        Optional<User> o_user = userRepo.findByEmail(email);
        // Nếu user không tồn tại thì báo lỗi
        if (!o_user.isPresent()) {
            throw new UserException("User is not found");
        }

        User user = o_user.get();
        // User muốn login phải có trạng thái Active
        if (user.getState() != State.ACTIVE) {
            throw new UserException("User is not activated");
        }

        // Kiểm tra password
        if (hashing.validatePassword(password, user.getHashed_password())) {
            return user;
        } else {
            throw new UserException("Password is incorrect");
        }
    }

    @Override
    public boolean logout(String email) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Boolean updatePassword(String email, String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean updateEmail(String email, String newEmail) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    // public void sendValidate(RegisterRequest registerRequest) {
    //     if (!userRepo.isEmailExist(registerRequest.email())) {
    //         User newUser = userRepo.addUser(registerRequest.name(), registerRequest.email(), registerRequest.password());
    //         String regisCode = UUID.randomUUID().toString();
    //         activateds.put(regisCode, newUser.getId());
    //         emailService.sendEmail(newUser.getEmail(), regisCode);
            
    //     }
        
    // }

    // @Override
    // public void checkValidate(String code) {
    //     User user = userRepo.findById(activateds.get(code));
    //     userRepo.addUserThenActivate(user.getFullname(), user.getEmail(), user.getHashed_password());
    //     activateds.remove(code);
        
    // }

}
