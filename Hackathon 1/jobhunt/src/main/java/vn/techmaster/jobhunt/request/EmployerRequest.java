package vn.techmaster.jobhunt.request;

import org.springframework.web.multipart.MultipartFile;

public record EmployerRequest(String name,String web,String gmail,MultipartFile logo) {

 
}
