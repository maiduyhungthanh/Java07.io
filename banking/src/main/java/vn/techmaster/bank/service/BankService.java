package vn.techmaster.bank.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.bank.exception.CommandException;
import vn.techmaster.bank.exception.RecordNotFoundException;
import vn.techmaster.bank.model.Account;
import vn.techmaster.bank.model.AccountSave;
import vn.techmaster.bank.model.CommandStatus;
import vn.techmaster.bank.model.Deposit;
import vn.techmaster.bank.model.Period;
import vn.techmaster.bank.model.User;
import vn.techmaster.bank.repository.AccountRepo;
import vn.techmaster.bank.repository.AccountSaveRepo;
import vn.techmaster.bank.repository.CommandRepo;
import vn.techmaster.bank.repository.UserRepo;
import vn.techmaster.bank.request.DepositRequest;
import vn.techmaster.bank.request.TransferRequest;
import vn.techmaster.bank.request.WithDrawRequest;
import vn.techmaster.bank.request.WithDrawSaveRequest;
import vn.techmaster.bank.response.AccountInfo;
import vn.techmaster.bank.response.AccountSaveInfo;
import vn.techmaster.bank.response.TransferInfo;

@Service
public class BankService {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private AccountRepo accountRepo;
  @Autowired
  private CommandRepo commandRepo;
  @Autowired
  private AccountSaveRepo accountSaveRepo;


  // Nạp Tiền
  @Transactional
  public AccountInfo deposit(DepositRequest depositRequest) {
    if (depositRequest.amount() <= 0) {
      throw new CommandException("Invalid transaction amount");
    }
    User user = userRepo.findById(depositRequest.userId())
        .orElseThrow(() -> new RecordNotFoundException("users", depositRequest.userId()));

    Account account = accountRepo.findById(depositRequest.accountId())
        .orElseThrow(() -> new RecordNotFoundException("account", depositRequest.accountId()));

    if (!account.getUser().getId().equals(depositRequest.userId())) {
      throw new CommandException("Requester is not owner of account");
    }

    account.setBalance(account.getBalance() + depositRequest.amount());
    Deposit deposit = new Deposit(user, account, depositRequest.amount());
    try {
      accountRepo.save(account);
      deposit.setStatus(CommandStatus.SUCCESS);
      commandRepo.save(deposit);
      return new AccountInfo(account.getId(), account.getBank().getName(), account.getBalance());
    } catch (Exception ex) {
      deposit.setStatus(CommandStatus.FAILED);
      commandRepo.save(deposit);
      var commandException = new CommandException("Deposit error");
      commandException.initCause(ex);
      throw commandException;
    }
  }

  // Rút Tiền TK Thường
  @Transactional
  public AccountInfo withDraw(WithDrawRequest withDrawRequest) {
    if (withDrawRequest.amount() <= 0) {
      throw new CommandException("Invalid transaction amount");
    }
    User user = userRepo.findById(withDrawRequest.userId())
        .orElseThrow(() -> new RecordNotFoundException("users", withDrawRequest.userId()));

    Account account = accountRepo.findById(withDrawRequest.accountId())
        .orElseThrow(() -> new RecordNotFoundException("account", withDrawRequest.accountId()));

    if (!account.getUser().getId().equals(withDrawRequest.userId())) {
      throw new CommandException("Requester is not owner of account");
    }
    if (account.getBalance() - withDrawRequest.amount() < 0) {
      throw new CommandException("The balance is not enough to make the transaction");
    }
    account.setBalance(account.getBalance() - withDrawRequest.amount());

    Deposit deposit = new Deposit(user, account, withDrawRequest.amount());
    try {
      accountRepo.save(account);
      deposit.setStatus(CommandStatus.SUCCESS);
      commandRepo.save(deposit);
      return new AccountInfo(account.getId(), account.getBank().getName(), account.getBalance());
    } catch (Exception ex) {
      deposit.setStatus(CommandStatus.FAILED);
      commandRepo.save(deposit);
      var commandException = new CommandException("Deposit error");
      commandException.initCause(ex);
      throw commandException;
    }
  }

  // Chuyển khoản
  @Transactional
  public TransferInfo Transfer(TransferRequest transferRequest) {
    if (transferRequest.amount() <= 0) {
      throw new CommandException("Invalid transaction amount");
    }
    User user_send = userRepo.findById(transferRequest.userId_send())
        .orElseThrow(() -> new RecordNotFoundException("users", transferRequest.userId_send()));

    Account account_send = accountRepo.findById(transferRequest.accountId_send())
        .orElseThrow(() -> new RecordNotFoundException("account", transferRequest.accountId_send()));

    User user_receive = userRepo.findById(transferRequest.userId_receive())
        .orElseThrow(() -> new RecordNotFoundException("users", transferRequest.userId_receive()));

    Account account_receive = accountRepo.findById(transferRequest.accountId_receive())
        .orElseThrow(() -> new RecordNotFoundException("account", transferRequest.accountId_receive()));

    if (!account_send.getUser().getId().equals(transferRequest.userId_send())) {
      throw new CommandException("Requester is not owner of account");
    }
    if (!account_receive.getUser().getId().equals(transferRequest.userId_receive())) {
      throw new CommandException("Recipient is not the owner of account");
    }
    if (account_send.getBalance() - transferRequest.amount() < 0) {
      throw new CommandException("The balance is not enough to make the transaction");
    }
    account_send.setBalance(account_send.getBalance() - transferRequest.amount());
    account_receive.setBalance(account_receive.getBalance() + transferRequest.amount());
    Deposit deposit = new Deposit(user_send, account_send, transferRequest.amount());
    try {
      accountRepo.save(account_send);
      accountRepo.save(account_receive);
      deposit.setStatus(CommandStatus.SUCCESS);
      commandRepo.save(deposit);
      return new TransferInfo(account_send.getId(), account_send.getBank().getName(), account_send.getBalance(),
          account_receive.getId(), account_receive.getBank().getName(), account_receive.getBalance());
    } catch (Exception ex) {
      deposit.setStatus(CommandStatus.FAILED);
      commandRepo.save(deposit);
      var commandException = new CommandException("Deposit error");
      commandException.initCause(ex);
      throw commandException;
    }
  }

    // Rút Tiền TK TIET KIEM
    @Transactional
    public AccountSaveInfo withDrawSave(WithDrawSaveRequest withDrawSaveRequest) {
      if (withDrawSaveRequest.amount() <= 0) {
        throw new CommandException("Số tiền giao dịch không hợp lệ");
      }
      User user = userRepo.findById(withDrawSaveRequest.userId())
          .orElseThrow(() -> new RecordNotFoundException("users", withDrawSaveRequest.userId()));
  
      AccountSave account = accountSaveRepo.findById(withDrawSaveRequest.accountSaveId())
          .orElseThrow(() -> new RecordNotFoundException("account", withDrawSaveRequest.accountSaveId()));
  
      if (!account.getUser().getId().equals(withDrawSaveRequest.userId())) {
        throw new CommandException("Người yêu cầu không phải là chủ sở hữu của tài khoản");
      }
      if (account.getBalance() - withDrawSaveRequest.amount() < 0) {
        throw new CommandException("Số dư không đủ để thực hiện giao dịch");
      }
      account.setBalance(account.getBalance() - withDrawSaveRequest.amount());
      // Nếu rút tiền thì TK Tiết Kiệm sẽ chuyển về TK Thường
      account.setMonths(Period.MONTH_of_0);
      account.setInterest_rate(0.001);
      Deposit deposit = new Deposit(user, account, withDrawSaveRequest.amount());
      try {
        accountSaveRepo.save(account);
        deposit.setStatus(CommandStatus.SUCCESS);
        commandRepo.save(deposit);
        return new AccountSaveInfo(account.getId(), account.getBank().getName(), account.getBalance(),account.getMonths().toString());
      } catch (Exception ex) {
        deposit.setStatus(CommandStatus.FAILED);
        commandRepo.save(deposit);
        var commandException = new CommandException("Deposit error");
        commandException.initCause(ex);
        throw commandException;
      }
    }
  // Tính lãi suất TK tiết kiệm
  public List<AccountSave> getAllAccountSaves() {
    for (AccountSave a : accountSaveRepo.findAll()) {
      // KHONG KY HAN
      if (a.getMonths().equals(Period.MONTH_of_0)) {
        if (accountRepo.findById(a.getAccount_id_nhan_lai()).isEmpty()) {
          if (accountSaveRepo.findById(a.getAccount_id_nhan_lai()).isEmpty()) {
          }
        }
        if (LocalDate.now().getDayOfYear() - a.getLocalDate().getDayOfYear() >= 30) {
          // lãi theo tháng
          Double l = a.getBalance() * (a.getInterest_rate() / 12);
          // nếu TK nhận lãi là 1 account Thường
          if (accountRepo.findById(a.getAccount_id_nhan_lai()).isPresent()) {
            Account account_nhan_lai = accountRepo.findById(a.getAccount_id_nhan_lai()).get();
            account_nhan_lai.setBalance(account_nhan_lai.getBalance() + l.longValue());
            // neu TK nhân lãi là 1 account Tiết Kiệm
          } else if (accountSaveRepo.findById(a.getAccount_id_nhan_lai()).isPresent()) {
            AccountSave accountSave_nhan_lai = accountSaveRepo.findById(a.getAccount_id_nhan_lai()).get();
            accountSave_nhan_lai.setBalance(accountSave_nhan_lai.getBalance() + l.longValue());
          }else{
            throw new RecordNotFoundException("account", a.getAccount_id_nhan_lai());
          }
          // Thay đổi ngày tính lãi gần nhất
          a.setLocalDate(LocalDate.now());
        }
        // KY HAN 6 THÁNG
      } else if (a.getMonths().equals(Period.MONTH_of_6)) {
        Double cuoi_ky_han = (double) 0;
        // cứ sau 30 ngày là tính lãi
        if (LocalDate.now().getDayOfYear() - a.getLocalDate().getDayOfYear() >= 30) {
          // lãi theo tháng
          Double l = a.getBalance() * (a.getInterest_rate() / 12) * 0.8;
          // lãi này để nhận cuối kỳ
          Double cuoi_ky = a.getBalance() * (a.getInterest_rate() / 12) * 0.2;
          cuoi_ky_han += cuoi_ky;
          // nếu TK nhận lãi là 1 account Thường
          if (accountRepo.findById(a.getAccount_id_nhan_lai()).isPresent()) {
            Account account_nhan_lai = accountRepo.findById(a.getAccount_id_nhan_lai()).get();
            account_nhan_lai.setBalance(account_nhan_lai.getBalance() + l.longValue());
            // neu TK nhân lãi là 1 account Tiết Kiệm
          } else if (accountSaveRepo.findById(a.getAccount_id_nhan_lai()).isPresent()) {
            AccountSave accountSave_nhan_lai = accountSaveRepo.findById(a.getAccount_id_nhan_lai()).get();
            accountSave_nhan_lai.setBalance(accountSave_nhan_lai.getBalance() + l.longValue());
          }else{
            throw new RecordNotFoundException("account", a.getAccount_id_nhan_lai());
          }
          // Thay đổi ngày tính lãi gần nhất
          a.setLocalDate(LocalDate.now());
        }
        // hết Kỳ hạn thì TK chuyển qua TK ko kỳ hạn
        if (LocalDate.now().getDayOfYear() - a.getStartDate().getDayOfYear() >= 30 * 6) {
          // nếu TK nhận lãi là 1 account Thường
          if (accountRepo.findById(a.getAccount_id_nhan_lai()).isPresent()) {
            Account account_nhan_lai = accountRepo.findById(a.getAccount_id_nhan_lai()).get();
            account_nhan_lai.setBalance(account_nhan_lai.getBalance() + cuoi_ky_han.longValue());
            // neu TK nhân lãi là 1 account Tiết Kiệm
          } else if (accountSaveRepo.findById(a.getAccount_id_nhan_lai()).isPresent()) {
            AccountSave accountSave_nhan_lai = accountSaveRepo.findById(a.getAccount_id_nhan_lai()).get();
            accountSave_nhan_lai.setBalance(accountSave_nhan_lai.getBalance() + cuoi_ky_han.longValue());
          }
          a.setMonths(Period.MONTH_of_0);
          a.setInterest_rate(0.001);
        }
        // KY HAN 1 THÁNG
      } else if (a.getMonths().equals(Period.MONTH_of_1)) {
        Double cuoi_ky_han = (double) 0;
        if (LocalDate.now().getDayOfYear() - a.getLocalDate().getDayOfYear() >= 30) {
          // lãi theo tháng
          Double l = a.getBalance() * (a.getInterest_rate()/ 12) * 0.8;
          // lãi này để nhận cuối kỳ
          Double cuoi_ky = a.getBalance() * (a.getInterest_rate() / 12) * 0.2;
          cuoi_ky_han += cuoi_ky;
          // nếu TK nhận lãi là 1 account Thường
          if (accountRepo.findById(a.getAccount_id_nhan_lai()).isPresent()) {
            Account account_nhan_lai = accountRepo.findById(a.getAccount_id_nhan_lai()).get();
            account_nhan_lai.setBalance(account_nhan_lai.getBalance() + l.longValue());
            // neu TK nhân lãi là 1 account Tiết Kiệm
          } else if (accountSaveRepo.findById(a.getAccount_id_nhan_lai()).isPresent()) {
            AccountSave accountSave_nhan_lai = accountSaveRepo.findById(a.getAccount_id_nhan_lai()).get();
            accountSave_nhan_lai.setBalance(accountSave_nhan_lai.getBalance() + l.longValue());
          }else{
            throw new RecordNotFoundException("account", a.getAccount_id_nhan_lai());
          }
          // Thay đổi ngày tính lãi gần nhất
          a.setLocalDate(LocalDate.now());
        }
        // hết Kỳ hạn thì TK chuyển qua TK ko kỳ hạn
        if (LocalDate.now().getDayOfYear() - a.getStartDate().getDayOfYear() >= 30 * 1) {
          // nếu TK nhận lãi là 1 account Thường
          if (accountRepo.findById(a.getAccount_id_nhan_lai()).isPresent()) {
            Account account_nhan_lai = accountRepo.findById(a.getAccount_id_nhan_lai()).get();
            account_nhan_lai.setBalance(account_nhan_lai.getBalance() + cuoi_ky_han.longValue());
            // neu TK nhân lãi là 1 account Tiết Kiệm
          } else if (accountSaveRepo.findById(a.getAccount_id_nhan_lai()).isPresent()) {
            AccountSave accountSave_nhan_lai = accountSaveRepo.findById(a.getAccount_id_nhan_lai()).get();
            accountSave_nhan_lai.setBalance(accountSave_nhan_lai.getBalance() + cuoi_ky_han.longValue());
          }
          a.setMonths(Period.MONTH_of_0);
          a.setInterest_rate(0.001);
        }
      }
    }
    return accountSaveRepo.findAll();
  }

  // List User
  public List<User> getAllUsers() {
    return userRepo.findAll();
  }

}
