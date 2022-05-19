package vn.techmaster.login.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.login.dto.UserDTO;
import vn.techmaster.login.exception.UserException;
import vn.techmaster.login.model.User;
import vn.techmaster.login.repository.UserRepo;
import vn.techmaster.login.request.LoginRequest;
import vn.techmaster.login.request.RegisterRequest;
import vn.techmaster.login.service.UserService;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @GetMapping
    public String showHomePage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println(session.getId());
        if (userDTO != null) {
            model.addAttribute("user", userDTO);
        }
        return "index";
    }

    @GetMapping("/login")
    public String HomeLogin(Model model) {
        model.addAttribute("loginrequest", new LoginRequest("", ""));
        return "login";
    }

    @PostMapping("login")
    public String handleLogin(@Valid @ModelAttribute("loginrequest") LoginRequest loginRequest,
            BindingResult result,
            HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }
        User user;
        try {
            user = userService.login(loginRequest.email(), loginRequest.password());
            session.setAttribute("user", new UserDTO(user.getId(), user.getFullname(), user.getEmail()));
            return "redirect:/";
        } catch (UserException ex) {
            switch (ex.getMessage()) {
                case "User is not found":
                    result.addError(new FieldError("loginrequest", "email", "Email does not exist"));
                    break;
                case "User is not activated":
                    result.addError(new FieldError("loginrequest", "email", "User is not activated"));
                    break;
                case "Password is incorrect":
                    result.addError(new FieldError("loginrequest", "password", "Password is incorrect"));
                    break;
            }
            return "login";
        }
    }

    // @GetMapping("/register")
    // public String HomeRegister(Model model) {
    // model.addAttribute("register",new RegisterRequest("","",""));
    //     return "register";
    // }
    // @PostMapping("/register")
    // public String Register(@Valid @ModelAttribute("register") RegisterRequest registerRequest, BindingResult result){
    //     if (result.hasErrors()) {
    //         return "register";
    //     }
    //     userService.sendValidate(registerRequest);

    //      return "index";
    // }

    // @GetMapping("/validate/{register-code}")
    // public String validateUser(@PathVariable("register-code")String code ){
    //    userService.checkValidate(code);
    //     return "index";
    // }

    @GetMapping("/foo")
    public String foo() {
        throw new UserException("User not found");
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

 
}
