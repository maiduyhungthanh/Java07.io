package vn.techmaster.jobhunt.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.jobhunt.model.Employer;
import vn.techmaster.jobhunt.repository.EmployerRepository;

@Controller
@RequestMapping("/")
public class EmployerController {
    @Autowired
    private EmployerRepository employerRepository;

    @GetMapping("/employer")
    public String getListEmployers(Model model) {
        model.addAttribute("emp", employerRepository.getEmployers());
        return "employer";
    }

    @GetMapping("/addemployer")
    public String addEmployer(Model model, Employer employer) {
        model.addAttribute("add", employer);
        return "addemployer";
    }

    @PostMapping("/save")
    public String saveAddEmployer(Employer employer) {
        List<String> listId = new ArrayList<>(employerRepository.getIdEmployers());
        
        if (listId.contains(employer.getId())) {
            employerRepository.updateEmployerById(employer,employer.getId());
        }else{
            employerRepository.createEmployer(employer);
        }
        return "redirect:/employer";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployer(@PathVariable String id) {
        employerRepository.deleteEmployerById(id);
        return "redirect:/employer";
    }

    @GetMapping("/edit/{id}")
    public String editEmployer(Model model, Employer employer) {
        String id = employer.getId();
        model.addAttribute("edit", employer);
        return "addemployer";
    }
}
