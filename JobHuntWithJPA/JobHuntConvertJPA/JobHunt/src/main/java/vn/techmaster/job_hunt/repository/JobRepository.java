package vn.techmaster.job_hunt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.model.Job;

import java.util.Collection;

@Repository
public interface JobRepository extends JpaRepository<Job,String> {
     Collection<Job> findJobsByEmployer_Id(String employer_id);
}

