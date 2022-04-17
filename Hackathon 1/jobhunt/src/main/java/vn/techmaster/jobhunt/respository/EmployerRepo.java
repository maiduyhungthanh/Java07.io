package vn.techmaster.jobhunt.respository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import vn.techmaster.jobhunt.model.Employer;

@Repository
public class EmployerRepo {
    private ConcurrentHashMap<String, Employer> employers = new ConcurrentHashMap<>();

    public EmployerRepo() {
    }

    // danh sach MAP(name+id)
    public Map<String,String> getMapNameId() {
        Map<String,String>mapEmp = new HashMap<>();
        List<Employer> listEmp = new ArrayList<>(employers.values().stream().toList());
        for(Employer e :listEmp){
            mapEmp.put(e.getName(),e.getId());
        }
        return mapEmp;
    }

    // can sinh ra id tu dong
    public Employer add(Employer employer) {
        String id = UUID.randomUUID().toString();
        employer.setId(id);
        employers.put(id, employer);
        return employer;
    }

    // danh sach Emloyer
    public Collection<Employer> getAll() {
        return employers.values();
    }

    public Employer findById(String id) {
        return employers.get(id);
    }

    // bổ sung dữ liệu Employer
    @PostConstruct
    public void addSomeData() {
        this.add(Employer.builder()
                .name("FPT")
                .logo("LogoFPT.jpg")
                .email("fpt@gmail.com")
                .web("https://fpt.com.vn").build());

        this.add(Employer.builder()
                .name("CMC")
                .logo("LogoCMC.png")
                .email("cmc@gmail.com")
                .web("https://cmc.com.vn").build());

        this.add(Employer.builder()
                .name("Amazon")
                .logo("amazon.png")
                .email("amazon@gmail.com")
                .web("https://amazon.com").build());

        this.add(Employer.builder()
                .name("Google")
                .logo("google.jpg")
                .email("google@gmail.com")
                .web("https://google.com").build());
    }
}
