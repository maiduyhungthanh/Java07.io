package vn.techmaster.job_hunt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.job_hunt.model.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer,String> {

}
