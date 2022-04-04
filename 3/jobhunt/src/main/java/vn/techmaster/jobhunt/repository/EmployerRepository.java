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

    // tao Employer new
    public Employer createEmployer(Employer employerNew){
        String uuid = UUID.randomUUID().toString();
        employerNew.setId(uuid);
        listEmployer.put(uuid, employerNew);
        return employerNew;
    }

//     // Sua Employer
//     public Employer updateEmployerById(String id,String name,String email) {
//       Employer updateEmployer = new Employer(id, name, email);
//       listEmployer.replace(id, updateEmployer);
//       return updateEmployer;
//     }
  
//     // Xoa Employer
//     public Employer deleteEmployerById(String id) {
//         Employer removedEmployer = listEmployer.remove(id);
//         return removedEmployer;
//     }
}
