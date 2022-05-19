package vn.techmaster.login.repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

import vn.techmaster.login.model.State;
import vn.techmaster.login.model.User;


@Repository
public class UserRepo {
  private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

  public User findById (String id){
    return users.get(id);
  } 

  public User addUserThenActivate(String fullName, String email, String hashed_pass) {
     User user = addUser(fullName, email, hashed_pass);
     user.setState(State.ACTIVE);
    return user;
  }
  public User addUser(String fullName, String email, String hashed_pass) {
    String id = UUID.randomUUID().toString();
    User user = User.builder()
    .id(id)
    .fullname(fullName)
    .email(email)
    .hashed_password(hashed_pass)
    .state(State.PENDING)
    .build();
    users.put(id, user);
    return user;
  }

  public boolean isEmailExist(String email) {
    return users.values().stream()
    .filter(user -> user.getEmail().equalsIgnoreCase(email)).count() > 0;
  }

  public Optional<User> findByEmail(String email) {
    return users.values().stream()
    .filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
  }

  public void update(User user) {
    users.put(user.getId(), user);
  }
}