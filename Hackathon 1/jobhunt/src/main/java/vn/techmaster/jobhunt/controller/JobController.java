package vn.techmaster.jobhunt.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.techmaster.jobhunt.model.City;
import vn.techmaster.jobhunt.model.Job;
import vn.techmaster.jobhunt.respository.EmployerRepo;
import vn.techmaster.jobhunt.respository.JobRepo;

@Controller

public class JobController {
    @Autowired private JobRepo jobRepo;
    @Autowired private EmployerRepo eRepo;
    @GetMapping("job")
    public String listJob (Model model){
        model.addAttribute("job", jobRepo.getAll());
        return "job";
    }

    @GetMapping("/addjob")
    public String addJob (Model model,Job job){
        model.addAttribute("city",City.values());
        model.addAttribute("name", eRepo.getAll());
        model.addAttribute("job", job);
        
        return "job_add";
    }

    @PostMapping("/savejob") 
    public String saveJob( @ModelAttribute("job")Job job) {
        List<String> listId = new ArrayList<>(jobRepo.getIdJobs());
        if (listId.contains(job.getId())) {

            jobRepo.update(job);
        }else{
            jobRepo.addJob(job);
        }
        return "redirect:/job";
    }

    @GetMapping("/deletejob/{id}")
    public String deleteJob(@PathVariable String id) {
        jobRepo.deleteById(id);
        return "redirect:/job";
    }

    @GetMapping("/editjob/{id}")
    public String editEmployer(@PathVariable("id") String id , Model model, Job job ) {
        id = job.getId();
        model.addAttribute("city",City.values());
        model.addAttribute("name", eRepo.getAll());
        model.addAttribute("job", job);
        return "job_add";
    }
}
