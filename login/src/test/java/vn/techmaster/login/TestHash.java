// package vn.techmaster.login;
// import static  org.assertj.core.api.Assertions.*;

// import java.util.List;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import vn.techmaster.login.hash.Hashing;

// @SpringBootTest
// public class TestHash {
//   @Autowired private Hashing hash;

//   @Test
//   public void hashPassword() {
//     var passwords = List.of("abc", "qwert", "ox-123", " _&?LLk2312");
//     for (String password : passwords) {
//       String hashed_pass = hash.hashPassword(password);
//       assertThat(hashed_pass).isNotNull();
//     }
//   }

//   @Test void validatePassword() {
//     var passwords = List.of("abc1232-+", "qwert19103", "ox-123", " _&?LLk2312");
//     for (String password : passwords) {
//       String hashed_pass = hash.hashPassword(password);
//       System.out.println(hashed_pass);  
//       assertThat(hash.validatePassword(password, hashed_pass)).isTrue();
//     }
//     assertThat(hash.validatePassword("abc1232-+", "1000:216c0ba94921bf4c76b00c38d62c3d7f:5c9c047bd6a8ea0453570f4002df47184830c0f7b376d8d1b8b05aa1f8b2927800cbf3aca50b21a25c3ea99dfbf6a2ef645a8f55afcdb3f28a829727afb9e6c9")).isFalse();
//   }
// }


