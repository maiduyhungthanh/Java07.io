package vn.techmaster.bank;

import java.time.LocalDate;
import java.time.Month;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import vn.techmaster.bank.exception.RecordNotFoundException;
import vn.techmaster.bank.model.Account;
import vn.techmaster.bank.model.AccountSave;
import vn.techmaster.bank.model.Bank;
import vn.techmaster.bank.model.Period;
import vn.techmaster.bank.model.User;
import vn.techmaster.bank.repository.AccountRepo;
import vn.techmaster.bank.repository.AccountSaveRepo;
import vn.techmaster.bank.repository.BankRepo;
import vn.techmaster.bank.repository.UserRepo;

@Component
@Transactional
public class AppRunner implements ApplicationRunner{
  @Autowired private UserRepo userRepo;
  @Autowired private AccountRepo accountRepo;
  @Autowired private AccountSaveRepo accountSaveRepo;
  @Autowired private BankRepo bankRepo;
  
  private void generateAccount() {
    Bank vcb = bankRepo.findById("vcb")


    .orElseThrow(() ->new RecordNotFoundException("bank", "vcb"));

    Bank acb = bankRepo.findById("acb")
    .orElseThrow(() ->new RecordNotFoundException("bank", "acb"));

    Bank vp = bankRepo.findById("vp")
    .orElseThrow(() ->new RecordNotFoundException("bank", "vp"));

    User bob = userRepo.findById("0001")
    .orElseThrow(() ->new RecordNotFoundException("users", "0001"));

    User alice = userRepo.findById("0002")
    .orElseThrow(() ->new RecordNotFoundException("users", "0002"));

    User tom = userRepo.findById("0003")
    .orElseThrow(() ->new RecordNotFoundException("users", "0003"));

    Account bob_vcb_1 = new Account("00012", vcb, bob, 1000L);
    accountRepo.save(bob_vcb_1);

    Account bob_vcb_2 = new Account("00013", vcb, bob, 0L);
    accountRepo.save(bob_vcb_2);

    Account alice_acb = new Account("00014", acb, alice, 500L);
    accountRepo.save(alice_acb);

    Account tom_acb = new Account("00015", acb, tom, 2000L);
    Account tom_vcb = new Account("00016", vcb, tom, 200L);
 
    accountRepo.save(tom_acb);
    accountRepo.save(tom_vcb);

    // Mở Tài Khoản Tiết Kiệm
    // TK Tiết kiệm 0 giới hạn
    AccountSave bob_vcb_save_1 =  new AccountSave("11111", vcb, bob, 1000L, Period.MONTH_of_0, LocalDate.of(2022, Month.MAY, 15), LocalDate.of(2022, Month.MAY, 15),"11111",0.001);
    accountSaveRepo.save(bob_vcb_save_1);
    // TK Tiết kiệm 6 tháng
    AccountSave bob_vcb_save_2 =  new AccountSave("22222", acb,bob , 1000L, Period.MONTH_of_6, LocalDate.of(2020, Month.MAY, 15),LocalDate.of(2022, Month.MAY, 15),"22222",0.065);
    accountSaveRepo.save(bob_vcb_save_2);
    // tk tiết kiệm 12 tháng
    AccountSave tom_vcb_save_1 =  new AccountSave("33333", acb,tom , 1000L, Period.MONTH_of_1,LocalDate.of(2022, Month.MAY, 15),LocalDate.of(2022, Month.MAY, 15),"11111",0.065);
    accountSaveRepo.save(tom_vcb_save_1);
  }


  @Override
  public void run(ApplicationArguments args) throws Exception {
    generateAccount();
  }
}
