package vn.techmaster.mp3.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import vn.techmaster.mp3.model.User;
import vn.techmaster.mp3.repository.UserRepository;

@Controller
public class MainController {
	@Autowired
	UserRepository userRepository;
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/user")
	public String showHomePage(Principal principal, Model model) {
	  String loginName = (principal != null) ? principal.getName() : "";
  
	  var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
  
	  for (var authority:authorities) {
		System.out.println(authority.getAuthority());
	  }
	  
	  model.addAttribute("login_name", loginName);
	  model.addAttribute("authorities", authorities);
	  return "aaaa";
	}
	@Operation(summary = "tất cả ca sỹ")
    @GetMapping(value = "/tendangnhap")
    public ResponseEntity<?> listSinger(Principal principal) {
		String loginName = (principal != null) ? principal.getName() : "";
		User userLogin = new User();
		for (User user : userRepository.findAll()) {
			if(user.getEmail().equals(loginName)){
				userLogin=user;
				break;
			}
		}
        return ResponseEntity.ok(userLogin);
    }
}
