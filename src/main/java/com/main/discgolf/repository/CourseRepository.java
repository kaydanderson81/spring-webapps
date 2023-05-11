package com.main.discgolf.repository;

import com.main.discgolf.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Boolean existsByName(String name);

    @Query(value = "SELECT DISTINCT r.course_id FROM Round r WHERE r.user_id = :userId", nativeQuery = true)
    List<Course> findAllCoursesAUserHasPlayedByUserId(@Param("userId") Long userId);
}
