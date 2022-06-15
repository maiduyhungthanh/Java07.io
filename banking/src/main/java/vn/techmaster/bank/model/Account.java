package vn.techmaster.bank.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  @Id
  private String id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private Bank bank; //Mỗi account phải mở ở một ngân hàng
  
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private User user; //Mỗi account phải gắn vào một user

  private Long balance;
}
