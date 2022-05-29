package vn.techmaster.job_hunt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.techmaster.job_hunt.model.*;
import vn.techmaster.job_hunt.repository.ApplicantRepository;
import vn.techmaster.job_hunt.repository.EmployerRepository;
import vn.techmaster.job_hunt.repository.JobRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.UUID;

@SpringBootApplication
public class JobHuntApplication implements CommandLineRunner {

	@Autowired
	private EmployerRepository employerRepository;

	@Autowired
	private JobRepository jobRepository;


	@Autowired
	private ApplicantRepository applicantRepository;

	public static void main(String[] args) {
		SpringApplication.run(JobHuntApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String id = UUID.randomUUID().toString();
		Employer employer = new Employer("1", "FPT", "fpt.png", "fpt.png", "bdoremonllk@gmail.com");
		employerRepository.save(employer);
		Job job = new Job("1", "Fullstack Java Developer", "Remote fulltime", City.HaNoi, LocalDateTime.now(), LocalDateTime.now(), employer);
		Job job1 = new Job("2", "Fullstack C# Developer", "Remote fulltime", City.HaNoi, LocalDateTime.now(), LocalDateTime.now(), employer);
		jobRepository.save(job);
		jobRepository.save(job1);

		Applicant applicant = new Applicant("1","Nakamura02","nakamura02@gmail.com","0977342466",new ArrayList<Skill>(EnumSet.allOf(Skill.class)),job);
		Applicant applicant1 = new Applicant("2","Nakamura01","nakamura02@gmail.com","0977342466",new ArrayList<Skill>(EnumSet.allOf(Skill.class)),job);
		applicantRepository.save(applicant);
		applicantRepository.save(applicant1);
	}
}
