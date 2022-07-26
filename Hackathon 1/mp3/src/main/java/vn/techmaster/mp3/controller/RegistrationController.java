package vn.techmaster.mp3.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import vn.techmaster.mp3.model.State;
import vn.techmaster.mp3.repository.UserRepository;
import vn.techmaster.mp3.request.UserRegistrationRequest;
import vn.techmaster.mp3.service.EmailService;
import vn.techmaster.mp3.service.UserService;



@Controller
@RequestMapping("/registration")
public class RegistrationController {
	@Autowired UserRepository userRepository;

	private UserService userService;

	public RegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("user")
    public UserRegistrationRequest userRegistrationDto() {
        return new UserRegistrationRequest();
    }
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationRequest userNew) {
		userService.save(userNew);	
		return "redirect:/registration?success";
	}

}
