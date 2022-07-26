package vn.techmaster.mp3.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vn.techmaster.mp3.model.Role;
import vn.techmaster.mp3.model.State;
import vn.techmaster.mp3.model.User;
import vn.techmaster.mp3.repository.UserRepository;
import vn.techmaster.mp3.request.UserRegistrationRequest;
import vn.techmaster.mp3.request.UserRequest;



@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired EmailService emailService;
	
	@Transactional
	public void generateUser() {
		// User admin = new User("", "Admin", "admin@gmail.com", passwordEncoder.encode("123"),  Arrays.asList(new Role("ROLE_ADMIN"),new Role("ROLE_USER"),new Role("ROLE_OPERATOR")));
		// User operator = new User("", "Operator", "operator@gmail.com", passwordEncoder.encode("123"),  Arrays.asList(new Role("ROLE_USER"),new Role("ROLE_OPERATOR")));
		// User user = new User("", "User", "user@gmail.com", passwordEncoder.encode("123"), Arrays.asList(new Role("ROLE_USER")));
		// admin.setState(State.ACTIVE);
		// operator.setState(State.ACTIVE);
		// user.setState(State.ACTIVE);
		// userRepository.save(admin);
		// userRepository.save(operator);
		// userRepository.save(user);
	}
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationRequest registrationDto) {
		User user = new User(registrationDto.getFirstName(), 
				registrationDto.getLastName(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));
		emailService.sendEmail(user.getEmail(),user.getId());
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Sai username hoặc password");
		}
		if (user.getState().equals(State.PENDING)){
			throw new  UsernameNotFoundException("Tài khoản chưa được kích hoạt");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	// Xác nhận tài khoản qua email
	public void getValidate(String id){
		User user = userRepository.findById(id).get();
		user.setState(State.ACTIVE);
		userRepository.save(user);
	}
	// Sửa User
	public User updateUser(UserRequest userRequest) {
        User user = userRepository.findById(userRequest.getId()).get();
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setAvatar(userRequest.getAvatar());
		userRepository.save(user);
        return user;
    }
	// All User
	public List<User> getAllUser(){
		return userRepository.findAll();
	}
}
