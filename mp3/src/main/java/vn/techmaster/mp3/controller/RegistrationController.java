package vn.techmaster.mp3.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.mp3.repository.UserRepository;
import vn.techmaster.mp3.request.UserRegistrationRequest;
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
