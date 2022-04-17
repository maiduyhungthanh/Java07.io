package vn.techmaster.jobhunt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {
  private String id;
  private String emp_id;
  private String employer;
  private String title;
  private String description;
  private String city;
}
