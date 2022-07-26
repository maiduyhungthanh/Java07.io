package vn.techmaster.mp3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.techmaster.mp3.service.SongSingerService;
import vn.techmaster.mp3.service.UserServiceImpl;


@Component
public class AppRunner implements CommandLineRunner {
  @Autowired private SongSingerService songSingerService;
  @Autowired private UserServiceImpl userServiceImpl;

  @Override
  public void run(String... args) throws Exception {
      // songSingerService.generateSongSinger();
      // userServiceImpl.generateUser();
  }

}
