package vn.techmaster.job_hunt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.repository.JobRepository;
import vn.techmaster.job_hunt.request.SearchRequest;
import vn.techmaster.job_hunt.response.JobReponse;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobServiceImple implements JobService{
    @Autowired
    private JobRepository jobRepository;

    @Override
    public Collection<Job> getAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job addJobForEmployer(Employer employer, Job job) {
        String id = UUID.randomUUID().toString();
        job.setId(id);
        job.setEmployer(employer);
        return jobRepository.save(job);
    }

    @Override
    public Job findById(String id) {
        Optional<Job> job = jobRepository.findById(id);
        return  job.get();
    }

    @Override
    public Collection<Job> findByEmpId(String empId) {
        return jobRepository.findJobsByEmployer_Id(empId);
    }

    @Override
    public Job deleteById(String id) {
        Optional<Job> job = jobRepository.findById(id);
        jobRepository.deleteById(id);
        return job.get();
    }

    @Override
    public void update(Job job) {
         jobRepository.save(job);
    }

    @Override
    public Collection<Job> filterJob(SearchRequest searchRequest) {
        Collection<Job> jobs = getAll();
        return  jobs.stream()
                .filter(job -> job.getTitle().toLowerCase()
                        .contains(searchRequest.getKeyword().toLowerCase())
                        && job.getCity().toString().equals(searchRequest.getCity().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public JobReponse pageJob(int page) {
        final int JOB_OF_PAGE = 6 ;
        Collection<Job> jobs = getAll();
        List<Job> jobList = getAll().stream().skip((page-1) * JOB_OF_PAGE).limit(JOB_OF_PAGE).collect(Collectors.toList());
        int totalPage = (int) Math.ceil((double) jobs.size()/JOB_OF_PAGE);
        return new JobReponse(jobList,totalPage);
    }
}
