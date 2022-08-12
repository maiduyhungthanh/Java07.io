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

import vn.techmaster.mp3.model.Comment;
import vn.techmaster.mp3.model.Post;
import vn.techmaster.mp3.model.Role;
import vn.techmaster.mp3.model.Song;
import vn.techmaster.mp3.model.SongUser;
import vn.techmaster.mp3.model.State;
import vn.techmaster.mp3.model.User;
import vn.techmaster.mp3.repository.CommentRepo;
import vn.techmaster.mp3.repository.PostRepo;
import vn.techmaster.mp3.repository.SongRepo;
import vn.techmaster.mp3.repository.SongUserRepo;
import vn.techmaster.mp3.repository.UserRepository;
import vn.techmaster.mp3.request.CommentRequest;
import vn.techmaster.mp3.request.CumentiRequest;
import vn.techmaster.mp3.request.PostRequest;
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
	@Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;

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
		Post post1 = new Post("Tiểu sử Ưng Hoàng Phúc", "Ưng Hoàng Phúc tên thật là Nguyễn Quốc Thanh (sinh ngày 18 tháng 8 năm 1981 tại xã Kiến An, Chợ Mới, An Giang) là một nam ca sĩ, diễn viên, nhà sản xuất điện ảnh, vũ công người Việt Nam", admin);
		Post post2 = new Post("Tiểu sử Quang Vinh", "Quang Vinh được phát hiện năng khiếu ca hát từ nhỏ, năm 11 tuổi Quang Vinh tham gia sinh hoạt ở đội Sơn Ca Nhà Thiếu Nhi Quận 1", admin);
		Post post3 = new Post("Tiểu sử Khánh Phương", "Khánh Phương (tên đầy đủ Phạm Khánh Phương, sinh ngày 4 tháng 11 năm 1981) là một ca sĩ nhạc trẻ Việt Nam, đồng thời còn là người sáng lập và cựu thành viên của nhóm nhạc MP5 (2001 - 2005)", operator);
		Comment cm1 = new Comment("idol 1 thời", post1, user);
		Comment cm2 = new Comment("tuổi thơ của 8x-9x", post2, user);
	
		userRepository.save(admin);
		userRepository.save(operator);
		userRepository.save(user);
	
		postRepo.save(post1);
		postRepo.save(post2);
		postRepo.save(post3);
		commentRepo.save(cm1);
		commentRepo.save(cm2);
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
	// Đóng góp ý kiến 
	public void sendCumenti(CumentiRequest cumentiRequest){
		if(cumentiRequest.getEmail() == null){
            cumentiRequest.setEmail("Người Ẩn Danh");
        }
		emailService.sendCumenti(cumentiRequest.getEmail(), cumentiRequest.getText());
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
	//User Detail
	public User UserById(String id){
		return userRepository.findById(id).get();
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
		public User changeRole(RoleRequest roleRequest) {
			User user = userRepository.findById(roleRequest.getId_user()).get();
			user.setRoles(null);
			if (roleRequest.getValue_role().equals("operator")) {
				User updateUser = new User(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getAvatar(), user.getState(), Arrays.asList(new Role("ROLE_USER"),new Role("ROLE_OPERATOR")), user.getSongUsers(),user.getPosts(),user.getComments());
				userRepository.save(updateUser);
			}else if(roleRequest.getValue_role().equals("user")){
				User updateUser = new User(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getAvatar(), user.getState(), Arrays.asList(new Role("ROLE_USER")), user.getSongUsers(),user.getPosts(),user.getComments());
				userRepository.save(updateUser);
			}else if(roleRequest.getValue_role().equals("admin")){
				User updateUser = new User(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getAvatar(), user.getState(), Arrays.asList(new Role("ROLE_USER"),new Role("ROLE_OPERATOR"),new Role("ROLE_ADMIN")), user.getSongUsers(),user.getPosts(),user.getComments());
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

		// tất cả các bài post
		public List<Post> getPostAll(){
			return postRepo.findAll();
		}
		// post theo id
		public Post getPostById(String id){
			return postRepo.findById(id).get();
		}

		// user comment trong bài post
		public Comment getCommentNew(CommentRequest commentRequest){
			Comment commentNew = new Comment(commentRequest.getText(),postRepo.findById(commentRequest.getPost_id()).get(),userRepository.findById(commentRequest.getUser_id()).get());
			commentRepo.save(commentNew);
			return commentNew;
		}
		// user viết post mới
		public Post getPostNew (PostRequest postRequest){
			Post postNew = new Post(postRequest.getTitle(), postRequest.getContent(), userRepository.findById(postRequest.getUser_id()).get());
			postRepo.save(postNew);
			return postNew;
		}
		// xóa bài Post
		public Post RemovePost(String id){
			postRepo.deleteById(id);
			return postRepo.findById(id).get();
		}
}
