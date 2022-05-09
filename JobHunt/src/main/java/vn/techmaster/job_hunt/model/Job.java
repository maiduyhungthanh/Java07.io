package vn.techmaster.job_hunt.model;

import java.time.LocalDateTime;

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
  private String title;
  private String description;
  private City city;
  private LocalDateTime update_time;
  private LocalDateTime create_time;
}
