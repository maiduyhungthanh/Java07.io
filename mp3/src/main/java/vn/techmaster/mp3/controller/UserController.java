package vn.techmaster.mp3.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import vn.techmaster.mp3.model.User;
import vn.techmaster.mp3.repository.UserRepository;
import vn.techmaster.mp3.service.UserServiceImpl;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserServiceImpl userServiceImpl;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@Operation(summary = "thông tin user đang đăng nhập")
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
	@Operation(summary = "kích hoạt user thông qua email")
	@GetMapping("/validate/{id}")
	public String validate(@PathVariable String id){
		userServiceImpl.getValidate(id);
		return "redirect:/login";
	}
	@GetMapping("/quantrivien")
	public String quantrivien(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "quantrivien";
	}
	@GetMapping("/user|{id}")
	public String getUserById(@PathVariable String id){
		if(userRepository.findById(id).isEmpty()){
			return "404";
		}
		return "userById";
	}
	@GetMapping("/userlikesong|{id}")
	public String getUserLikeSong(@PathVariable String id){
		if(userRepository.findById(id).isEmpty()){
			return "404";
		}
		return "userLikeSong";
	}
	@Operation(summary = "kích hoạt user thông qua email")
	@GetMapping("/role/{role}/{id}")
	public String changeRole(@PathVariable String id,@PathVariable String role){
		// userServiceImpl.changeRole(id,role);
		return  "redirect:/quantrivien";
	}
}
