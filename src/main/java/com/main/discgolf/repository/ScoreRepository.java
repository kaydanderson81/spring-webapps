package com.main.discgolf.repository;

import com.main.discgolf.model.Score;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository  extends CrudRepository<Score, Long> {

    @Query(value = "SELECT s.score from score s WHERE s.round_id = :id", nativeQuery = true)
    List<Integer> findAllScoresByRoundId(@Param("id") Long id);
}
