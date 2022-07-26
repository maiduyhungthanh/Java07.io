package vn.techmaster.mp3.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
import vn.techmaster.mp3.model.Song;
import vn.techmaster.mp3.model.SongUser;
import vn.techmaster.mp3.model.State;
import vn.techmaster.mp3.model.User;
import vn.techmaster.mp3.repository.SongRepo;
import vn.techmaster.mp3.repository.SongUserRepo;
import vn.techmaster.mp3.repository.UserRepository;
import vn.techmaster.mp3.request.RoleRequest;
import vn.techmaster.mp3.request.UserLikeSong;
import vn.techmaster.mp3.request.UserRegistrationRequest;
import vn.techmaster.mp3.request.UserRequest;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	@Autowired
	SongRepo songRepo;
	@Autowired
	SongUserRepo songUserRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	EmailService emailService;

	@Transactional
	public void generateUser() {
		User admin = new User("", "Admin", "admin@gmail.com",
		passwordEncoder.encode("123"), Arrays.asList(new Role("ROLE_ADMIN"),new
		Role("ROLE_USER"),new Role("ROLE_OPERATOR")));
		User operator = new User("", "Operator", "operator@gmail.com",
		passwordEncoder.encode("123"), Arrays.asList(new Role("ROLE_USER"),new
		Role("ROLE_OPERATOR")));
		User user = new User("", "User", "user@gmail.com",
		passwordEncoder.encode("123"), Arrays.asList(new Role("ROLE_USER")));
		admin.setState(State.ACTIVE);
		operator.setState(State.ACTIVE);
		user.setState(State.ACTIVE);
		userRepository.save(admin);
		userRepository.save(operator);
		userRepository.save(user);
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
		emailService.sendEmail(user.getEmail(), user.getId());
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Sai username hoặc password");
		}
		if (user.getState().equals(State.PENDING)) {
			throw new UsernameNotFoundException("Tài khoản chưa được kích hoạt");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	// Xác nhận tài khoản qua email
	public void getValidate(String id) {
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
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	// Song yeu thích trong User
	public Song getSongLike(UserLikeSong userLikeSong) {
		for (SongUser songUser : songUserRepo.findAll()) {
			if (songUser.getSong().getId().equals(userLikeSong.getId_song())
					&& songUser.getUser().getId().equals(userLikeSong.getId_user())) {
				return null;
			}
		}
		Song song = songRepo.findById(userLikeSong.getId_song()).get();
		User user = userRepository.findById(userLikeSong.getId_user()).get();
		SongUser songUser = new SongUser(song, user);
		songUserRepo.save(songUser);
		return song;
	}

	// lay danh sach nhac theo User
	public List<Song> getUserLikeSong(String id) {
		Optional<User> user = userRepository.findById(id);
		List<String> ids = new ArrayList<>();
		for (String id_song : user.get().getUsers().keySet()) {
			ids.add(id_song);
		}
		List<Song> songs = songRepo.findAll().stream().filter(s -> ids.contains(s.getId()))
				.collect(Collectors.toList());
		return songs;
	}

	// xóa bài hát trong DS yêu thích
	public SongUser deleteSongOfUser(String id_song, String id_user) {
		SongUser songUser = songUserRepo.findAll().stream()
				.filter(s -> s.getSong().getId().equals(id_song) && s.getUser().getId().equals(id_user)).findFirst()
				.get();
		songUserRepo.delete(songUser);
		return songUser;
	}

	// thay doi chuc nang Role
	public void changeRole(String id, String role) {
		User user = userRepository.findById(id).get();
		if (role.equals("admin")) {
			user.setRoles(Arrays.asList(new Role("ROLE_ADMIN"), new Role("ROLE_USER"), new Role("ROLE_OPERATOR")));
		} else if (role.equals("operator")) {
			user.setRoles(Arrays.asList(new Role("ROLE_USER"), new Role("ROLE_OPERATOR")));
		} else if (role.equals("user")) {
			user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		}
		userRepository.save(user);
	}
		// thay doi chuc nang Role
		public User changeRole(RoleRequest roleRequest) {
			User user = userRepository.findById(roleRequest.getId_user()).get();
			user.setRoles(null);
			if (roleRequest.getValue_role().equals("operator")) {
				User updateUser = new User(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getAvatar(), user.getState(), Arrays.asList(new Role("ROLE_USER"),new Role("ROLE_OPERATOR")), user.getSongUsers());
				userRepository.save(updateUser);
			}else if(roleRequest.getValue_role().equals("user")){
				User updateUser = new User(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getAvatar(), user.getState(), Arrays.asList(new Role("ROLE_USER")), user.getSongUsers());
				userRepository.save(updateUser);
			}else if(roleRequest.getValue_role().equals("admin")){
				User updateUser = new User(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getAvatar(), user.getState(), Arrays.asList(new Role("ROLE_USER"),new Role("ROLE_OPERATOR"),new Role("ROLE_ADMIN")), user.getSongUsers());
				userRepository.save(updateUser);
			}
			return user;
		}
		// xóa người dùng
		public User deleteUser(RoleRequest roleRequest){
			User user = userRepository.findById(roleRequest.getId_user()).get();
			user.setRoles(null);
			userRepository.delete(user);
			return user;
		}
}
