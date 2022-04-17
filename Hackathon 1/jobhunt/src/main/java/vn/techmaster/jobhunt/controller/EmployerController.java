package vn.techmaster.jobhunt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.jobhunt.request.EmployerRequest;
import vn.techmaster.jobhunt.respository.EmployerRepo;

@Controller
@RequestMapping("/employer")
public class EmployerController {
    @Autowired private EmployerRepo employerRepo;
    @GetMapping
    public String listAllEmployer (Model model){
        model.addAttribute("emp", employerRepo.getAll());
        return "employer";
    }
    @GetMapping(value="{id}")
    public String showEmployerDataiById(Model model,@PathVariable String id){
        model.addAttribute("e", employerRepo.findById(id));
        return "employer_by_id";
    }
    @GetMapping(value = "/add")
    public String addEmployerForm(Model model){
        model.addAttribute("employer", new EmployerRequest("","","", null));
        return "employer_add";
    }
    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public String addEmployer(@ModelAttribute EmployerRequest employerRequest, BindingResult result){
        if(result.hasErrors()){
            return "employer_add";
        }
        return "redirect:/employer";
}
}