package vn.techmaster.demobank.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="customer")
@Table(name="customer")
@Data
@NoArgsConstructor
public class User {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String password;
  private Integer phone;

  public User(String name,String password,Integer phone) {
    this.name = name;
    this.password = password;
    this.phone = phone;
  }

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private List<Account> accounts = new ArrayList<>();
  public void addAccount(Account account) {
    account.setUser(this);
    accounts.add(account);
  }

}
