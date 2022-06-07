package vn.techmaster.demobank;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.techmaster.demobank.service.AccountUserService;



@Component
public class AppRunner implements CommandLineRunner {
  @Autowired private AccountUserService accountUserService;


  @Override
  public void run(String... args) throws Exception {
      accountUserService.generateAccountUser();
  }

}
