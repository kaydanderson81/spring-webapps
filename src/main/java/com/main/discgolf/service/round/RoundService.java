package com.main.discgolf.service.round;

import com.main.discgolf.model.Course;
import com.main.discgolf.model.Hole;
import com.main.discgolf.model.Round;
import com.main.discgolf.model.RoundArray;

import java.text.ParseException;
import java.util.List;

public interface RoundService {

    List<Round> getAllRounds();

    List<Round> getAllScoresByRoundId(Long id);

    Round getRoundById(Long id);

    Round saveRound(Round round);

    void deleteRoundById(Long id);

    List<List<Round>> getAllRoundsInListsByCourseId();

    List<Round> getAllRoundsByCourseId(Long courseId);

    int getBestRoundScoreByCourseId(Long userId, Long courseId);

    List<Integer> getListOfScoresByRoundId(Long id);

    List<RoundArray> getListOfRoundArrays();

    double getAverageScoreByCourse(Long userId, Long courseId);

    Round addDateAndScoresToRound(String date, List<Integer> scores, Course course) throws ParseException;
}
