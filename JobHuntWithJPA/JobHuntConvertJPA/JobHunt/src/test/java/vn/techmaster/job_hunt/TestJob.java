package vn.techmaster.job_hunt;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import vn.techmaster.job_hunt.model.*;
import vn.techmaster.job_hunt.repository.ApplicantRepository;
import vn.techmaster.job_hunt.repository.EmployerRepository;
import vn.techmaster.job_hunt.repository.JobRepository;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.UUID;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestJob {
    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;


    @Autowired
    private ApplicantRepository applicantRepository;

    @Test
    @Transactional
    void addEmployer() {
        //add
        String id = UUID.randomUUID().toString();
        Employer employer = new Employer(id, "FPT", "https://fpt.com.vn", "fpt.png", "bdoremonllk@gmail.com");
        employerRepository.save(employer);
    }

    @Test
    @Transactional
    void addJob() {
        //add
        String id = UUID.randomUUID().toString();
        Employer employer = new Employer(id, "FPT", "https://fpt.com.vn", "fpt.png", "bdoremonllk@gmail.com");
        employerRepository.save(employer);
        Job job = new Job(UUID.randomUUID().toString(), "Fullstack Java Developer", "Remote fulltime", City.HaNoi, LocalDateTime.now(), LocalDateTime.now(), employer);
        Job job1 = new Job(UUID.randomUUID().toString(), "Fullstack C# Developer", "Remote fulltime", City.HaNoi, LocalDateTime.now(), LocalDateTime.now(), employer);
        jobRepository.save(job);
        jobRepository.save(job1);
        assertThat(job.getEmployer()).isEqualTo(employer);
    }

    @Test
    @Transactional
    void updateJob() {
        //add
        String id = UUID.randomUUID().toString();
        Employer employer = new Employer(id, "FPT", "https://fpt.com.vn", "fpt.png", "bdoremonllk@gmail.com");
        employerRepository.save(employer);
        Job job = new Job(UUID.randomUUID().toString(), "Fullstack Java Developer", "Remote fulltime", City.HaNoi, LocalDateTime.now(), LocalDateTime.now(), employer);
       // Job job1 = new Job(UUID.randomUUID().toString(), "Fullstack C# Developer", "Remote fulltime", City.HaNoi, LocalDateTime.now(), LocalDateTime.now(), employer);
        jobRepository.save(job);
        job.setCity(City.DaNang);
        jobRepository.save(job);
        System.out.println(job);
        assertThat(job.getEmployer()).isEqualTo(employer);
    }
    @Test
    @Transactional
    void deleteJob() {
        //add
        String id = UUID.randomUUID().toString();
        Employer employer = new Employer(id, "FPT", "https://fpt.com.vn", "fpt.png", "bdoremonllk@gmail.com");
        employerRepository.save(employer);
        Job job = new Job(UUID.randomUUID().toString(), "Fullstack Java Developer", "Remote fulltime", City.HaNoi, LocalDateTime.now(), LocalDateTime.now(), employer);
        // Job job1 = new Job(UUID.randomUUID().toString(), "Fullstack C# Developer", "Remote fulltime", City.HaNoi, LocalDateTime.now(), LocalDateTime.now(), employer);
        jobRepository.save(job);


        //remove
        jobRepository.delete(job);
        assertThat(job.getEmployer()).isEqualTo(employer);
    }


    @Test
    @Transactional
    void addApplicant() {
        //add
        String id = UUID.randomUUID().toString();
        Employer employer = new Employer(id, "FPT", "https://fpt.com.vn", "fpt.png", "bdoremonllk@gmail.com");
        employerRepository.save(employer);
        Job job = new Job(UUID.randomUUID().toString(), "Fullstack Java Developer", "Remote fulltime", City.HaNoi, LocalDateTime.now(), LocalDateTime.now(), employer);
        // Job job1 = new Job(UUID.randomUUID().toString(), "Fullstack C# Developer", "Remote fulltime", City.HaNoi, LocalDateTime.now(), LocalDateTime.now(), employer);
        jobRepository.save(job);

        Applicant applicant = new Applicant(UUID.randomUUID().toString(),"Nakamura02","nakamura02@gmail.com","0977342466",new ArrayList<Skill>(EnumSet.allOf(Skill.class)),job);
        Applicant applicant1 = new Applicant(UUID.randomUUID().toString(),"Nakamura01","nakamura02@gmail.com","0977342466",new ArrayList<Skill>(EnumSet.allOf(Skill.class)),job);
        applicantRepository.save(applicant);
        applicantRepository.save(applicant1);
        System.out.println(applicant);
        System.out.println(applicant1);



    }
}