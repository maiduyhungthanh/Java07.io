// package vn.techmaster.login;

// import org.junit.jupiter.api.Test;

// import vn.techmaster.login.model.State;
// import vn.techmaster.login.model.User;
// import vn.techmaster.login.repository.UserRepo;

// import static org.assertj.core.api.Assertions.*;

// ////////@SpringBootTest
// public class TestUserRepo {
//   @Test
//   public void addUser() {
//     UserRepo userRepo = new UserRepo();
//     User user = userRepo.addUser("John Levy", "levy@gmail.com", "OX-1321am21321");
//     assertThat(user).isNotNull();
//     System.out.println(user.getId());
//     assertThat(user.getId()).isNotBlank();
//   }

//   @Test
//   public void addUserWithPendingState() {
//     UserRepo userRepo = new UserRepo();
//     User user = userRepo.addUser("John Levy", "levy@gmail.com", "OX-1321am21321");
//     assertThat(user).isNotNull();
//     assertThat(user.getId()).isNotBlank();
//     assertThat(user.getState()).isEqualTo(State.PENDING);
//   }

//   @Test
//   public void isEmailExist() {
//     UserRepo userRepo = new UserRepo();
//     userRepo.addUser("John Levy", "levy@gmail.com", "OX-1321am21321");
//     userRepo.addUser("Quốc Thái", "thainq@gmail.com", "OX-324433421");
//     userRepo.addUser("Mạnh Cường", "cuong85@gmail.com", "OX-3244213094861");

//     assertThat(userRepo.isEmailExist("levy@gmail.com")).isTrue();
//     assertThat(userRepo.isEmailExist("thaiNQ@gmail.com")).isTrue();
//     assertThat(userRepo.isEmailExist("cuong85@gmail.com")).isTrue();
//     assertThat(userRepo.isEmailExist("Cuong85@gmail.com")).isTrue();
//     assertThat(userRepo.isEmailExist("cuong86@gmail.com")).isFalse();
//   }

//   @Test
//   public void findByEmail() {
//     UserRepo userRepo = new UserRepo();
//     userRepo.addUser("John Levy", "levy@gmail.com", "OX-1321am21321");
//     userRepo.addUser("Quốc Thái", "thainq@gmail.com", "OX-324433421");
//     userRepo.addUser("Mạnh Cường", "cuong85@gmail.com", "OX-3244213094861");

//     assertThat(userRepo.findByEmail("levy@Gmail.COM")).isPresent();
//     assertThat(userRepo.findByEmail("thaiNQ@gmail.com")).isPresent();
//     assertThat(userRepo.findByEmail("thaiNQ1@gmail.com")).isNotPresent();

//   }
// }
