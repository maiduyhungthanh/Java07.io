package vn.techmaster.bank.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Command {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User requester; // Người yêu cầu hành động này

  public Command(User requester) {
    this.requester = requester;
  }

  private CommandStatus status;
  private LocalDateTime startAt;
  private LocalDateTime finishAt;

  @PrePersist // Khi lệnh bắt đầu chạy
  public void prePersist() {
    startAt = LocalDateTime.now();
  }

  @PreUpdate //Khi lệnh chạy xong
  public void preUpdate() {
    finishAt = LocalDateTime.now();
  }
}
