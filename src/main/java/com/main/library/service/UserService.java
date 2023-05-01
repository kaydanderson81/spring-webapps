package com.main.library.service;
import java.security.Principal;
import java.util.List;

import com.main.discgolf.model.Score;
import com.main.library.model.Role;
import com.main.library.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService{
	List<User> getAllUsers();
	User saveUser(User user);
	User getUserById(Long id);
	User getUserByEmail(String email);
	void deleteUserById(Long id);
	List<Role> getRoles();

	Long getUserIdByPrincipalName(Principal principal);
	
//	Page<User> findPaginatedUsers(int pageNo, int pageSize, String sortField, String sortDirection);

	Page<User> findPaginatedUsers(int pageNo);

	Page<User> findAllWithSort(String field, String direction, int pageNo);
	
	int getNumberOfUsers(List<User> users);
	
	boolean isUniqueEmailViolated(Long id, String email);

	double getAverageScoreByUser(Long userId);

	int getNumberOfAces(Long userId);

	List<Score> getListOfAllScoresForUserByUserId(Long userId);
	
}
