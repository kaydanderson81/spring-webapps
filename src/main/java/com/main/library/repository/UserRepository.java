package com.main.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.library.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);

	@Query(value = "SELECT s.score from score s WHERE s.round_id = :id", nativeQuery = true)
	List<Integer> getBestRoundByCourseId(@Param("id") Long id);
	
	Optional<User> findById(Long id);


	
//	@Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.email = ?2")
//	@Modifying
//	public void updateFailedAttempt(int failedAttempt, String email);
	
}
