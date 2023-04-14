package com.main.discgolf.repository;

import com.main.discgolf.model.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Boolean existsByName(String name);
}
