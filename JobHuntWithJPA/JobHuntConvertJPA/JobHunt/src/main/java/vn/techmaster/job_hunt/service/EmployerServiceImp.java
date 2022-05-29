package vn.techmaster.job_hunt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.repository.EmployerRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmployerServiceImp implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Override
    public Collection<Employer> getAll() {
        return employerRepository.findAll();
    }

    @Override
    public Optional<Employer> findById(String id) {
        return employerRepository.findById(id);
    }

    @Override
    public void updateLogo(String id, String logo_path) {
        Optional<Employer> employer = findById(id);
        employer.get().setLogo_path(logo_path);
        employerRepository.save(employer.get());
    }

    @Override
    public Employer add(Employer employer) {
        String id = UUID.randomUUID().toString();
        employer.setId(id);
        employerRepository.save(employer);
        return employer;
    }

    @Override
    public void edit(Employer employer) {
        employerRepository.save(employer);
    }

    @Override
    public void deleteById(String id) {
        employerRepository.deleteById(id);
    }

    @Override
    public ConcurrentHashMap<String, Employer> getAllEmployerHashMap() {
        ConcurrentHashMap<String, Employer> results = new ConcurrentHashMap<>();
        List<Employer> employers = employerRepository.findAll();
        for (int i = 0; i < employers.size(); i++) {
              results.put(employers.get(i).getId(),employers.get(i));
        }
        return results;
    }
}
