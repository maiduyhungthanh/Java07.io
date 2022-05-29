package vn.techmaster.job_hunt.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.service.JobService;

@RestController
@RequestMapping("/api")
public class api {
    @Autowired
    JobService jobService;
    @GetMapping("/x")
    public ResponseEntity<?> listSinger(){
        Collection<Job> listJob = jobService.getAll();
        return ResponseEntity.ok(listJob);
    }
}
