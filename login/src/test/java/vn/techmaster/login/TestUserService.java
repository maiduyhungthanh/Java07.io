// package vn.techmaster.login;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import vn.techmaster.login.exception.UserException;
// import vn.techmaster.login.model.User;
// import vn.techmaster.login.service.UserService;

// import static org.assertj.core.api.Assertions.*;

// @SpringBootTest
// public class TestUserService {
//   @Autowired private UserService userService;

//   @Test
//   public void addUser() {
//     User user = userService.addUser("Trinh Minh Cuong", "cuong@techmaster.vn", "abc1234-");
//     assertThat(user).isNotNull();
//   }

//   @Test
//   public void login_when_account_is_pending() {
//     userService.addUser("Trinh Minh Cuong", "cuong@techmaster.vn", "abc1234-");
//     assertThatThrownBy(() -> {
//       userService.login("cuong@techmaster.vn", "abc1234-");
//     }).isInstanceOf(UserException.class)
//     .hasMessageContaining("User is not activated");
//   }

//   @Test
//   public void login_when_account_is_not_found() {
//     assertThatThrownBy(() -> {
//       userService.login("dung@techmaster.vn", "abc1234-");
//     }).isInstanceOf(UserException.class)
//     .hasMessageContaining("User is not found");
//   }

//   @Test
//   public void login_when_password_is_incorrect() {
//     userService.addUserThenAutoActivate("Trinh Minh Cuong", "cuong@techmaster.vn", "abc1234-");
//     assertThatThrownBy(() -> {
//       userService.login("cuong@techmaster.vn", "abc1234+");
//     }).isInstanceOf(UserException.class)
//     .hasMessageContaining("Password is incorrect");
//   }

//   @Test
//   public void login_successful() {
//     userService.addUserThenAutoActivate("Trinh Minh Cuong", "cuong@techmaster.vn", "abc1234-");
//     User user = userService.login("cuong@techmaster.vn", "abc1234-");
//     assertThat(user).isNotNull();
//   }
// }
