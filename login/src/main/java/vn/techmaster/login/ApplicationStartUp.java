package vn.techmaster.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import vn.techmaster.login.service.UserService;


@Component
public class ApplicationStartUp implements ApplicationRunner {
    @Autowired UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.addUserThenAutoActivate("Khiêm", "khiem31200@gmail.com", "123@abc");
        userService.addUser("Khiêm", "khiem312005@gmail.com", "123@abc");
    }
    
}
