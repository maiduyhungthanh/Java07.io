package vn.techmaster.job_hunt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.request.JobRequest;
import vn.techmaster.job_hunt.request.SearchRequest;
import vn.techmaster.job_hunt.response.JobReponse;
import vn.techmaster.job_hunt.service.ApplicantService;
import vn.techmaster.job_hunt.service.EmployerService;
import vn.techmaster.job_hunt.service.JobService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private ApplicantService applicantService;

    @GetMapping("/home")
    public String listAllJob(Model model) {
      return  pageJob(1,model);
    }

    @GetMapping()
    public String pageJob(@RequestParam int page,Model model){
        JobReponse jobReponse = jobService.pageJob(page);
        model.addAttribute("searchRequest",new SearchRequest());
        model.addAttribute("totalPage",jobReponse.getTotalPage());
        model.addAttribute("jobs", jobReponse.getJobs());
        model.addAttribute("employers", employerService.getAllEmployerHashMap());
        model.addAttribute("totalApplicantMap", applicantService.countApplicantTotal());
        return "job_home";
    }
    @GetMapping(value = "admin/{id}")
    public String showJobDetailByID(Model model, @PathVariable String id) {
        Job job = jobService.findById(id);
        model.addAttribute("job", job);
        model.addAttribute("employer", employerService.findById(job.getEmployer().getId()));
        model.addAttribute("applicants", applicantService.findApplicantsByJob_id(job.getId()));
        return "job";
    }

    @GetMapping(value = "/{id}")
    public String showJobApplyByID(Model model, @PathVariable String id) {
        Job job = jobService.findById(id);
        model.addAttribute("job", job);
        model.addAttribute("employer", employerService.findById(job.getEmployer().getId()));
        return "job_apply";
    }

    @GetMapping(value = "/add/{emp_id}")
    public String addEmployerForm(Model model, @PathVariable String emp_id) {
        model.addAttribute("job", new JobRequest("", emp_id, "", "", null));
        return "job_add";
    }


    @GetMapping(value = "/search")
    public String searchKeyword(@RequestBody @ModelAttribute("searchRequest") SearchRequest searchRequest, Model model) {
        model.addAttribute("jobs", jobService.filterJob(searchRequest));
        model.addAttribute("employers", employerService.getAllEmployerHashMap());
        model.addAttribute("totalApplicantMap", applicantService.countApplicantTotal());
        return "job_home";
    }

    @PostMapping(value = "/add")
    public String addEmployer(@Valid @ModelAttribute("job") JobRequest jobRequest,
                              BindingResult result,
                              Model model) {

        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "job_add";
        }

        Job newJob  = new Job();
        newJob.setTitle(jobRequest.title());
        newJob.setDescription(jobRequest.description());
        newJob.setCity(jobRequest.city());

        Optional<Employer> employer = employerService.findById(jobRequest.emp_id());

        // Thêm vào cơ sở dữ liệu
        jobService.addJobForEmployer(employer.get(),newJob);

        // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
        return "redirect:/employer/" + jobRequest.emp_id();
    }

    @GetMapping(value = "/edit/{id}")
    public String editJobId(Model model, @PathVariable("id") String id) {
        Optional<Job> job = Optional.of(jobService.findById(id));
        if (job.isPresent()) {
            Job currentJob = job.get();
            model.addAttribute("jobReq", new JobRequest(
                    currentJob.getId(),
                    currentJob.getEmployer().getId(),
                    currentJob.getTitle(),
                    currentJob.getDescription(),
                    currentJob.getCity()));
            // model.addAttribute("job", currentJob);
            model.addAttribute("employer", employerService.findById(currentJob.getEmployer().getId()));
        }
        return "job_edit";
    }

    @PostMapping(value = "/edit")
    public String edit(@Valid @ModelAttribute("jobReq") JobRequest jobRequest,
                       BindingResult result,
                       Model model) {

        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "job_edit";
        }

        // Thêm vào cơ sở dữ liệu
        jobService.update(Job.builder()
                .id(jobRequest.id())
                .employer(employerService.findById(jobRequest.emp_id()).get())
                .title(jobRequest.title())
                .description(jobRequest.description())
                .city(jobRequest.city()).build());

        // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
        return "redirect:/employer/" + jobRequest.emp_id();
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteJobByID(@PathVariable String id) {
        Job jobDel = jobService.deleteById(id);
        return "redirect:/employer/" + jobDel.getEmployer().getId();
    }
}
