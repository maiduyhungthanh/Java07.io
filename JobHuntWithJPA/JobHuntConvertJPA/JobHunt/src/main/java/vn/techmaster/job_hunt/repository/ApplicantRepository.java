package vn.techmaster.job_hunt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.job_hunt.model.Applicant;

import java.util.Collection;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant,String> {
    public Collection<Applicant> findApplicantsByJob_Id(String id);
}
