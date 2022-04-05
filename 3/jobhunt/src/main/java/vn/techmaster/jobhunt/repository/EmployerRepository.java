package vn.techmaster.jobhunt.repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import vn.techmaster.jobhunt.model.Employer;

@Component
public class EmployerRepository {
    ConcurrentHashMap<String, Employer> listEmployer = new ConcurrentHashMap<>();
    //them danh sach Job
    public EmployerRepository(){
        listEmployer.put("01", new Employer("01", "FPT","fpt@gmail.com"));
        listEmployer.put("02", new Employer("02", "CMC", "cmc@gmail.com"));
        listEmployer.put("03", new Employer("03", "Tesmaster","fpt@gmail.com"));
    }

    //danh sach Job
    public List<Employer> getEmployers(){
        return listEmployer.values().stream().toList();
    }
    //danh sach id
    public List<String> getIdEmployers(){
        return listEmployer.keySet().stream().toList();
    }
    //tìm theo id
    public Employer findById(String id){
        return listEmployer.get(id);
    }
    // tao Employer new
    public Employer createEmployer(Employer employerNew){
        String uuid = UUID.randomUUID().toString();
        employerNew.setId(uuid);
        listEmployer.put(uuid, employerNew);
        return employerNew;
    }

    // Sua Employer
    public Employer updateEmployerById(Employer employerInput,String id) {
      Employer updateEmployer = listEmployer.get(id);
      updateEmployer.setName(employerInput.getName());
      updateEmployer.setEmail(employerInput.getEmail());
      return updateEmployer;
    }
  
    // Xoa Employer
    public List<Employer> deleteEmployerById(String id) {
        listEmployer.remove(id);
        return listEmployer.values().stream().toList();
    }
}
