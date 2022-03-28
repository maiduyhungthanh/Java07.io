package vn.techmaster.joblisting.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.joblisting.model.Job;
import vn.techmaster.joblisting.model.Location;

@RestController
@RequestMapping("/job")
public class JobController {
    private ConcurrentHashMap<String, Job> jobs;
  public JobController() {
    jobs = new ConcurrentHashMap<>();
    jobs.put("01", new Job("01", "Java", "Fresher", Location.DaNang,1000,3500,"kien@gmail.com"));
    jobs.put("02", new Job("02", "C++", "Junior", Location.HaNoi,1100,4500,"son@gmail.com"));
    jobs.put("03", new Job("03", "Phyton", "Senior", Location.HoChiMinh,1800,5500,"vu@gmail.com"));
    jobs.put("04", new Job("04", "NodeJS", "Lead Developer", Location.HaiPhong,1700,6500,"thanh@gmail.com"));
  }

  //danh sách job
  @GetMapping("/all")
  public List<Job> getJobs() {
    return jobs.values().stream().toList();
  }

  //sắp xếp danh sách job theo thành phố tuyển
  @GetMapping("/sortbylocation")
  public List<Job> getJobsByTitle() {
    
    return jobs.values().stream()
    .sorted(Comparator.comparing(Job::getLocation).reversed())
    .collect(Collectors.toList());
  }

  //tao them Job moi
  // @PostMapping("/create")
  // public Job createNewJob(@RequestParam String title,@RequestParam String location,
  // @RequestParam String description,@RequestParam int minSalary,@RequestParam int maxSalary,
  // @RequestParam String email) {
  //   String uuid = UUID.randomUUID().toString();
  //   Job newJob = new Job(uuid,title,location,description,maxSalary,minSalary,email);
  //   jobs.put(uuid, newJob);
  //   return newJob;
  // }

 

  // tìm các job mà {salary} trong khoảng min_salary và max_salary
  @GetMapping("/salary/{salary}")
  public List<Job> minToMaxSalary(@PathVariable  int salary){
    

    return jobs.values().stream().filter(n ->n.getMin_salary() <= salary && n.getMax_salary() >= salary)
    .collect(Collectors.toList());
  }
  
  //http://localhost:8080/job/keyword/{keyword} tìm các job mà title hoặc description chứa {keyword}
  @GetMapping("/keyword/{keyword}")
  public List<Job> getKeyWord(@PathVariable String keyword){
    return jobs.values().stream().filter(n ->n.getTitle().toLowerCase().contains(keyword.toLowerCase())||n.getDescription().toLowerCase().contains(keyword.toLowerCase()))
    .collect(Collectors.toList());
  }

  //http://localhost:8080/job/query?location={location}&keyword={keyword} tìm các job mà title hoặc description chứa {keyword} đồng thời location ={location}
  //@RequestParam(value = "location",required=true) Location location,
  @GetMapping("/query")
  public List<Job> getKeyWordByLocation(@RequestParam Location location,@RequestParam String keyword){
    return jobs.values().stream().filter(n ->n.getTitle().toLowerCase().contains(keyword.toLowerCase())||n.getDescription().toLowerCase().contains(keyword.toLowerCase())
    ||n.getLocation().equals(location))
    .collect(Collectors.toList());
  }
}