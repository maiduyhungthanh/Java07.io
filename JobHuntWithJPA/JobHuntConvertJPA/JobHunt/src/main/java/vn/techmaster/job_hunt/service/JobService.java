package vn.techmaster.job_hunt.service;

import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.request.SearchRequest;
import vn.techmaster.job_hunt.response.JobReponse;

import java.util.Collection;

public interface JobService {
    public Collection<Job> getAll();
    public Job addJobForEmployer(Employer employer, Job job);
    public Job findById(String id);
    public Collection<Job> findByEmpId(String empId);
    public Job deleteById(String id);
    public void update(Job job);
    public Collection<Job> filterJob(SearchRequest searchRequest);
    public JobReponse pageJob(int page);
}
