package vn.techmaster.bank.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity(name="users")
@Table(name="users")
public class User {
  @Id
  private String id;

  private String name;
  private String email;
  private String mobile;
  
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private List<Account> accounts = new ArrayList<>();

  public void addAccount(Account account) {
    account.setUser(this);
    accounts.add(account);    
  }

  public void removeAccount(Account account) {
    account.setUser(null);
    accounts.remove(account);   
  }

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private List<AccountSave> accountsaves = new ArrayList<>();

  public void addAccount(AccountSave accountsave) {
    accountsave.setUser(this);
    accountsaves.add(accountsave);    
  }

  public void removeAccount(AccountSave account) {
    account.setUser(null);
    accounts.remove(accountsaves);   
  }
}
