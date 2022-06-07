package vn.techmaster.demobank.model;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "account")
@Table(name = "account")
@Data
@NoArgsConstructor
public class Account {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private Integer amount;
  @Column
  @ElementCollection(targetClass=String.class)
  private List<String> history ;

  public Account(String name,Integer amount) {
    this.name = name;
    this.amount = amount;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinColumn(name = "user_id")
  private User user;
}
