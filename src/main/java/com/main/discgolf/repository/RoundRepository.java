package com.main.discgolf.repository;

import com.main.discgolf.model.Hole;
import com.main.discgolf.model.Round;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends CrudRepository<Round, Long> {

//    Round findByCourseName(String courseName);

    @Query(value = "SELECT * FROM score s WHERE s.round_id = :id", nativeQuery = true)
    List<Round> findAllScoresByRoundId(@Param("id")Long id);

    @Query(value = "SELECT * FROM round r WHERE r.course_id = :id", nativeQuery = true)
    List<Round> findAllRoundsByCourseId(@Param("id")Long id);

    @Query(value = "SELECT * FROM round r WHERE r.user_id = :id", nativeQuery = true)
    List<Round> findAllRoundsByUserId(@Param("id")Long id);

}
