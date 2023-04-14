package com.main.discgolf.repository;

import com.main.discgolf.model.Hole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoleRepository extends CrudRepository<Hole, Long> {

    @Query(value = "SELECT * FROM hole h WHERE h.course_id = :id", nativeQuery = true)
    List<Hole> findAllHolesByCourseId(@Param("id")Long id);
}
