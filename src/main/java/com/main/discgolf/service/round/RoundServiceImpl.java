package com.main.discgolf.service.round;

import com.main.discgolf.model.*;
import com.main.discgolf.repository.RoundRepository;
import com.main.discgolf.repository.ScoreRepository;
import com.main.discgolf.service.course.CourseService;
import com.main.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoundServiceImpl implements RoundService {

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Override
    public List<Round> getAllRounds() {
        return (List<Round>) this.roundRepository.findAll();
    }

    @Override
    public List<Round> getAllScoresByRoundId(Long id) {
        return this.roundRepository.findAllScoresByRoundId(id);
    }

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
    public List<List<Round>> getAllRoundsInListsByCourseId() {
        List<Round> allRounds = getAllRounds();
        List<List<Round>> allRoundsByCourseId = new ArrayList<>();
        for (Round round : allRounds) {
            for (int i=0; i< allRounds.size(); i++) {
                List<Round> roundsByCourseId = roundRepository.findAllRoundsByCourseId((long) i);
                allRoundsByCourseId.add(roundsByCourseId);

            }
        }
        return allRoundsByCourseId;
    }

    @Override
    public List<Round> getAllRoundsByCourseId(Long courseId) {
        return roundRepository.findAllRoundsByCourseId(courseId);
    }

    @Override
    public Round addDateAndScoresToRound(String date, List<Integer> scores, Course course) throws ParseException {
        Round round = new Round();
        List<Integer> parList = courseService.getCourseListOfHolePars(course);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = df.parse(date);
        Integer sum = scores.stream()
                .reduce(0, Integer::sum);

        round.setCourse(course);
        round.setRoundDate(startDate);
        round.setTotal(sum);
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
        List<Round> rounds = roundRepository.findAllRoundsByUserId(userId);
        rounds.removeIf(round -> !round.getCourse().getId().equals(courseId));
        IntSummaryStatistics summaryStatistics = rounds.stream().mapToInt(Round::getTotal).summaryStatistics();
        return summaryStatistics.getMin();
    }

    @Override
    public List<Integer> getListOfScoresByRoundId(Long id) {
        return scoreRepository.findAllScoresByRoundId(id);
    }

    @Override
    public List<RoundArray> getListOfRoundArrays() {
        List<Round> rounds = getAllRounds();
        List<RoundArray> roundArrays = new ArrayList<>();
        for (Round round : rounds) {
            RoundArray roundArray = new RoundArray();
            List<Integer> scoreArray = getListOfScoresByRoundId(round.getRoundId());
            roundArray.setId(round.getRoundId());
            roundArray.setRoundArray(scoreArray);
            roundArrays.add(roundArray);
        }
        return roundArrays;
    }

    @Override
    public double getAverageScoreByCourse(Long userId, Long courseId) {
        List<Round> rounds = userService.getUserById(userId).getRounds();
        return rounds.stream().filter(r -> r.getCourse().getId().equals(courseId)).collect(Collectors.averagingDouble(Round::getTotal));
    }
}
