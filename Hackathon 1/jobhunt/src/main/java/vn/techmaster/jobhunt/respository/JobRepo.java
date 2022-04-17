package vn.techmaster.jobhunt.respository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.techmaster.jobhunt.model.Job;

@Repository
public class JobRepo {
  private ConcurrentHashMap<String, Job> jobs = new ConcurrentHashMap<>();

  @Autowired
  private EmployerRepo eRepo;

  
  public Collection<Job> getAll(){
    for (Job j:jobs.values().stream().toList()){
      for(Map.Entry<String,String>emp : eRepo.getMapNameId().entrySet()){
        if(j.getEmployer()==emp.getKey()){
          j.setEmp_id(emp.getValue());
        }
      }
    }
    return jobs.values();
  }

  //danh sach id
  public List<String> getIdJobs(){
       return jobs.keySet().stream().toList();
  }
  // public Optional<Job> get(String id) {
  //   return employers.values().stream().filter(u -> u.getId().contains(id)).findFirst();
  // }

  //thêm Job
  public Job addJob(Job job) {
    String id = UUID.randomUUID().toString();
    job.setId(id);
    jobs.put(id, job);
    return job;
  }

  //tim job theo id
  public Job findById(String id) {
    return jobs.get(id);
  }

  //xoa job
  public Job deleteById(String id) {
    return jobs.remove(id);
   
  }

  //update job
  public void update(Job job){
    jobs.put(job.getId(), job);
  }

    //thêm dữ liệu Job
    @PostConstruct
    public void addDataJob(){
      this.addJob(Job.builder()
      // .emp_id(listEmpId().get(0))
      .employer("FPT")
      .title("Tuyển Fresh")
      .description("Tuyển Fresh fullStack lương 1000 usb,yêu cầu tốt nghiệp techmaster")
      .city("Hà Nội").build());

      this.addJob(Job.builder()
      // .emp_id(listEmpId().get(1))
      .employer("CMC")
      .title("Tuyển Thực Tập Sinh")
      .description("Tuyển Thực tập sinh Backend Java lương 1000 usb,yêu cầu tốt nghiệp techmaster")
      .city("Hồ Chí Minh").build());

      this.addJob(Job.builder()
      // .emp_id(listEmpId().get(2))
      .employer("Amazon")
      .title("Tuyển FrontEnd")
      .description("Tuyển FrontEnd lương 1000 usb,yêu cầu tốt nghiệp techmaster")
      .city("Hải Phòng").build());

      this.addJob(Job.builder()
      // .emp_id(listEmpId().get(3))
      .employer("Google")
      .title("Tuyển Junior")
      .description("Tuyển Jonior,Thao thạo 2 ngoại ngữ,làm chủ được SpringBoot")
      .city("Đà Nẵng").build());
    }

}
