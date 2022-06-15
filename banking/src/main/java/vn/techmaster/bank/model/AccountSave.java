package vn.techmaster.bank.model;

import javax.persistence.Enumerated;

import java.time.LocalDate;

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
public class AccountSave {
  @Id
  private String id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private Bank bank; //Mỗi account phải mở ở một ngân hàng
  
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private User user; //Mỗi account phải gắn vào một user

  private Long balance;

  @Enumerated
  private Period months;

  // ngày mở TK
  private LocalDate startDate;
  // ngày tính lãi suất gần nhất
  private LocalDate localDate;

  private String account_id_nhan_lai;
  private Double interest_rate;
}

