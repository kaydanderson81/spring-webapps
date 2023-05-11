package com.main.library.service;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.main.discgolf.model.Course;
import com.main.discgolf.model.Round;
import com.main.discgolf.model.Score;
import com.main.discgolf.repository.CourseRepository;
import com.main.discgolf.repository.ScoreRepository;
import com.main.discgolf.service.round.RoundServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.library.model.Role;
import com.main.library.model.User;
import com.main.library.repository.RoleRepository;
import com.main.library.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	@Autowired 
	private RoleRepository roleRepository;

	@Autowired
	private RoundServiceImpl roundService;

	@Autowired
	private ScoreRepository scoreRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	private final CourseRepository courseRepository;


	public UserServiceImpl(UserRepository userRepository,
						   CourseRepository courseRepository) {
		super();
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.getUserByEmail(email);//return user object from the database (findByEmail)
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	

	
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public int getNumberOfUsers(List<User> users) {
		return userRepository.findAll().size();
	}

	@Override
	public User saveUser(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		Role roleUser = roleRepository.findByName("User");
		if (user.getRoles() == null) {
			user.addRole(roleUser);
		}
		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()){
			return user.get();
		} else {
			throw new RuntimeException("User not found for id:" + id);
		}

	}
	
	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}



	@Override
	public void deleteUserById(Long id) {
		this.userRepository.deleteById(id);
		
	}
	
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Long getUserIdByPrincipalName(Principal principal) {
		String name = principal.getName();
		List<User> users = userService.getAllUsers();
		for (User user : users) {
			if (user.getEmail().equals(name)) {
				return user.getId();
			}
		}
		return null;
	}

	@Override
	public Page<User> findPaginatedUsers(int pageNo) {
		Pageable pageable = PageRequest.of(pageNo -1, 5);
		return userRepository.findAll(pageable);
	}

	@Override
	public Page<User> findAllWithSort(String field, String direction, int pageNo) {
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())?
				Sort.by(field).ascending(): Sort.by(field).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, 5, sort);
		return userRepository.findAll(pageable);
	}

	public boolean isUniqueEmailViolated(Long id, String email) {
		boolean isUniqueEmailViolated = false;
		
		User userByEmail = userRepository.findByEmail(email);
		boolean isCreatingNew = (id == null || id == 0);
		
		if (isCreatingNew) {
			if (userByEmail != null) isUniqueEmailViolated = true; 
		} else {
			if (userByEmail != null) {
				isUniqueEmailViolated = true;
			}
		}
		return isUniqueEmailViolated;
	}

	@Override
	public double getAverageScoreByUser(Long userId) {
		List<Integer> scores = new ArrayList<>();
		List<Round> rounds = userService.getUserById(userId).getRounds();
		for (Round round : rounds) {
			for (Score score : round.getScores()) {
				scores.add(score.getScore());
			}
		}
		return scores.stream().mapToDouble(d -> d).average().orElse(0.0);
	}

	@Override
	public int getNumberOfAces(Long userId) {
		List<Round> rounds = userService.getUserById(userId).getRounds();
		int aces = 0;
		for (Round round : rounds) {
			for (Score score : round.getScores()) {
				if (score.getScore() == 1) {
					aces += 1;
				}
			}
		}
		return aces;
	}

	@Override
	public List<Score> getListOfAllScoresForUserByUserId(Long userId) {
		List<Round> rounds = userService.getUserById(userId).getRounds();
		List<Score> scores = new ArrayList<>();
		for (Round round : rounds) {
			scores.addAll(round.getScores());
		}
		return scores;
	}

}
