package vn.techmaster.job_hunt.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employer {
  @Id
  private String id;

  private String name;

  private String logo_path;

  private String website;

  private String email;

  public Employer(String id, String name, String logo_path, String website, String email) {
    this.id = id;
    this.name = name;
    this.logo_path = logo_path;
    this.website = website;
    this.email = email;
  }

  @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = false)
  private List<Job> jobs = new ArrayList<>();


}
