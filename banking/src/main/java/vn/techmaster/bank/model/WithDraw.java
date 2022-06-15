package vn.techmaster.bank.model;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

// Rút tiền
public class WithDraw extends Command {
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
  
    private Long amount;
  
    public WithDraw(User requester, Account account,  Long amount) {
      super(requester);
      this.account = account;
      this.amount = amount;
    }
}
