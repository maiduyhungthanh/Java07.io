package vn.techmaster.job_hunt.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.request.JobRequest;
import vn.techmaster.job_hunt.request.SearchRequest;
import vn.techmaster.job_hunt.respository.ApplicantRepo;
import vn.techmaster.job_hunt.respository.EmployerRepo;
import vn.techmaster.job_hunt.respository.JobRepo;

@Controller
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private JobRepo jobRepo;
    @Autowired
    private EmployerRepo empRepo;
    @Autowired
    private ApplicantRepo applicantRepo;

    @GetMapping()
    public String pageJob(Model model){
        model.addAttribute("searchRequest", new SearchRequest());
        model.addAttribute("jobs", jobRepo.getAll());
        model.addAttribute("employers", empRepo.getAllEmployerHashMap());
        model.addAttribute("totalApplicantMap", applicantRepo.countApplicantsTotal());
        return "job_home";
    }


    @GetMapping(value = "/search")
    public String searchKeyword(@RequestBody @ModelAttribute("searchRequest") SearchRequest searchRequest , Model model) {
        model.addAttribute("jobs", jobRepo.filterJob(searchRequest));
        model.addAttribute("employers", empRepo.getAllEmployerHashMap());
        model.addAttribute("totalApplicantMap", applicantRepo.countApplicantsTotal());
        return "job_home";
    }

    @GetMapping(value = "admin/{id}")
    public String showJobDetailByID(Model model, @PathVariable String id) {
        Job job = jobRepo.findById(id);
        model.addAttribute("job", job);
        model.addAttribute("employer", empRepo.findById(job.getEmp_id()));
        model.addAttribute("applicants", applicantRepo.findByJobId(id));
        return "job";
    }

    @GetMapping(value = "/{id}")
    public String showJobApplyByID(Model model, @PathVariable String id) {
        Job job = jobRepo.findById(id);
        model.addAttribute("job", job);
        model.addAttribute("employer", empRepo.findById(job.getEmp_id()));
        model.addAttribute("applicants", applicantRepo.findById(id));
        return "job_apply";
    }

    @GetMapping(value = "/add/{emp_id}")
    public String addEmployerForm(Model model, @PathVariable String emp_id) {
        model.addAttribute("job", new JobRequest("", emp_id, "", "", null));
        return "job_add";
    }


    @PostMapping(value = "/add")
    public String addEmployer(@Valid @ModelAttribute("job") JobRequest jobRequest,
                              BindingResult result,
                              Model model) {

        // N???? c?? l???i th?? tr??? v??? tr??nh duy???t
        if (result.hasErrors()) {
            return "job_add";
        }

        // Th??m v??o c?? s??? d??? li???u
        jobRepo.addJob(Job.builder()
                .emp_id(jobRequest.emp_id())
                .title(jobRequest.title())
                .description(jobRequest.description())
                .city(jobRequest.city()).build());

        // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
        return "redirect:/employer/" + jobRequest.emp_id();
    }

    @GetMapping(value = "/edit/{id}")
    public String editJobId(Model model, @PathVariable("id") String id) {
        Optional<Job> job = Optional.of(jobRepo.findById(id));
        if (job.isPresent()) {
            Job currentJob = job.get();
            model.addAttribute("jobReq", new JobRequest(
                    currentJob.getId(),
                    currentJob.getEmp_id(),
                    currentJob.getTitle(),
                    currentJob.getDescription(),
                    currentJob.getCity()));
            // model.addAttribute("job", currentJob);
            model.addAttribute("employer", empRepo.findById(currentJob.getEmp_id()));
        }
        return "job_edit";
    }

    @PostMapping(value = "/edit")
    public String edit(@Valid @ModelAttribute("jobReq") JobRequest jobRequest,
                       BindingResult result,
                       Model model) {

        // N???? c?? l???i th?? tr??? v??? tr??nh duy???t
        if (result.hasErrors()) {
            return "job_edit";
        }

        // Th??m v??o c?? s??? d??? li???u
        jobRepo.update(Job.builder()
                .id(jobRequest.id())
                .emp_id(jobRequest.emp_id())
                .title(jobRequest.title())
                .description(jobRequest.description())
                .city(jobRequest.city()).build());

        // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
        return "redirect:/employer/" + jobRequest.emp_id();
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteJobByID(@PathVariable String id) {
        Job jobDel = jobRepo.deleteById(id);
        return "redirect:/employer/" + jobDel.getEmp_id();
    }
}
