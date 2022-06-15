package vn.techmaster.bank.model;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class WithDrawSave extends Command {
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountSave accountsSave;
  
    private Long amount;
  
    public WithDrawSave(User requester, AccountSave accountSave,  Long amount) {
      super(requester);
      this.accountsSave = accountSave;
      this.amount = amount;
    }
}
