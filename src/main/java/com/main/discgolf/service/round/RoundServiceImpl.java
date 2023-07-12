package com.main.discgolf.service.round;

import com.main.discgolf.model.Course;
import com.main.discgolf.model.CourseByRound;
import com.main.discgolf.model.Round;
import com.main.discgolf.model.Score;
import com.main.discgolf.repository.RoundRepository;
import com.main.discgolf.repository.ScoreRepository;
import com.main.discgolf.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RoundServiceImpl implements RoundService {

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CourseService courseService;

    @Override
    public Round getRoundById(Long id) {
        Optional<Round> optional = roundRepository.findById(id);
        Round round;
        if (optional.isPresent()) {
            round = optional.get();
        } else {
            throw new RuntimeException("Round not found for id :: " + id);
        }
        return round;
    }

    @Override
    public Round saveRound(Round round) {
        return this.roundRepository.save(round);
    }

    @Override
    public void deleteRoundById(Long id) {
        this.roundRepository.deleteById(id);
    }

    @Override
    public void updateRecordIfRoundIsDeleted(Course course, Round round) {
        List<Round> rounds = roundRepository.findAllRoundsByCourseId(course.getId());
        IntSummaryStatistics intSummaryStatistics = rounds.stream()
                .map(Round::getTotal)
                .mapToInt(Integer::intValue).summaryStatistics();
        course.setRecord(intSummaryStatistics.getMin() - course.getPar());
        courseService.saveCourse(course);
    }

    @Override
    public List<Round> listOfRoundsByCourseByRound(List<CourseByRound> courseByRounds) {
        List<Round> jsonRounds = new ArrayList<>();
        for (CourseByRound courseByRound : courseByRounds) {
            jsonRounds.addAll(courseByRound.getRounds());
        }
        return jsonRounds;
    }

    @Override
    public Round addDateAndScoresToRound(String date, List<Integer> scores, Course course, boolean playedAlone) throws ParseException {
        Round round = new Round();
        List<Integer> parList = courseService.getCourseListOfHolePars(course);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = df.parse(date);
        Integer sum = scores.stream()
                .reduce(0, Integer::sum);

        round.setCourse(course);
        round.setRoundDate(startDate);
        round.setTotal(sum);
        round.setPlayedAlone(playedAlone);
        List<Score> scoreList = new ArrayList<>();

        for (int i=0; i< parList.size(); i++) {
            Score newScore = new Score();
            int scr = scores.get(i);
            int par = parList.get(i);
            newScore.setScore(scr);
            newScore.setHolePar(par);
            if (scr == (par-3)) {
                newScore.setName("Albatross");
                newScore.setColor("#2851A3");
            }
            if (scr == (par-2)) {
                newScore.setName("Eagle");
                newScore.setColor("#3E6BC7");
            }
            if (scr == (par-1)) {
                newScore.setName("Birdie");
                newScore.setColor("#77ACD8");
            }
            if (scr == par) {
                newScore.setName("Par");
                newScore.setColor("#eee");
            }
            if (scr == (par + 1)) {
                newScore.setName("Bogie");
                newScore.setColor("#FBD59C");
            }
            if (scr == (par + 2)) {
                newScore.setName("Double Bogie");
                newScore.setColor("#F9BF68");
            }
            if (scr == (par + 3)) {
                newScore.setName("Triple Bogie");
                newScore.setColor("#F8AB36");
            }
            if (scr >= (par + 4)) {
                newScore.setName("oops!");
                newScore.setColor("#FA1100");
            }
            if (scr == 1) {
                newScore.setName("Ace");
                newScore.setColor("#66C698");
            }
            scoreList.add(newScore);
        }
        round.setScores(scoreList);
        return round;
    }

    @Override
    public int getBestRoundScoreByCourseId(Long userId, Long courseId) {
        List<Round> rounds = roundRepository.findAllRoundsByUserIdAndCourseId(userId, courseId);
        IntSummaryStatistics intSummaryStatistics = rounds.stream()
                .map(Round::getTotal)
                .mapToInt(Integer::intValue).summaryStatistics();
        return intSummaryStatistics.getMin();
    }

    @Override
    public double getAverageScoreByCourse(Long userId, Long courseId) {
        List<Round> rounds = roundRepository.findAllRoundsByUserIdAndCourseId(userId, courseId);
        List<Double> totalList = new ArrayList<>();
        for (Round round : rounds) {
            totalList.add((double) round.getTotal());
        }
        return totalList.stream().mapToDouble(val -> val).average().orElse(0.0);
    }

    @Override
    public List<Integer> getListOfScoresByRoundId(Long id) {
        return scoreRepository.findAllScoresByRoundId(id);
    }



}
