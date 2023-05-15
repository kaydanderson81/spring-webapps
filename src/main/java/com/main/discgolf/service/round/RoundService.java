package com.main.discgolf.service.round;

import com.main.discgolf.model.Course;
import com.main.discgolf.model.CourseByRound;
import com.main.discgolf.model.Round;

import java.text.ParseException;
import java.util.List;

public interface RoundService {

    Round getRoundById(Long id);

    Round saveRound(Round round);

    void deleteRoundById(Long id);

    List<Round> listOfRoundsByCourseByRound(List<CourseByRound> courseByRounds);

    List<Integer> getListOfScoresByRoundId(Long id);

    Round addDateAndScoresToRound(String date, List<Integer> scores, Course course) throws ParseException;

    int getBestRoundScoreByCourseId(Long userId, Long courseId);

    double getAverageScoreByCourse(Long userId, Long courseId);
}
