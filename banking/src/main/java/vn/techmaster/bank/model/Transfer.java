package vn.techmaster.bank.model;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class Transfer extends Command {
  
    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account_receive;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account_send;

    private User user_receive;
  
    public Transfer(User user_send, Account account_send,User user_receive,Account account_receive,  Long amount) {
      super(user_send);
      this.account_send=account_send;
      this.account_receive=account_receive;
      this.user_receive=user_receive;
      this.amount = amount;
    }
}
