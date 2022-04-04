package vn.techmaster.jobhunt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.techmaster.jobhunt.model.Employer;
import vn.techmaster.jobhunt.repository.EmployerRepository;

@Controller
@RequestMapping("/")
public class EmployerController {
@Autowired
    private EmployerRepository employerRepository;

    @GetMapping("/employer")
    public String getListEmployers(Model model){
        model.addAttribute("emp",employerRepository.getEmployers());
        return "employer";
    }

    @GetMapping("/addemployer")
    public String addEmployer(Model model , Employer employer){
        model.addAttribute("add", employer);
        return "addemployer";
    }

    @PostMapping("/save")
    public String saveAddEmployer (Employer employer){
        employerRepository.createEmployer(employer);
        return "redirect:/employer";
    }

    // @GetMapping("/updateemployer")
    // public String updateEmployer(Model model,@PathVariable String id,@PathVariable String name,@PathVariable String email){
    //     model.addAttribute("up", employerRepository.updateEmployerById(id, name, email));
    //     return "editemployer";
    // }
 
    // @PostMapping("/saveemployer")
    // public String getSaveEmployer(){
    //     return "redirect:/employer";
    // }
    
}
