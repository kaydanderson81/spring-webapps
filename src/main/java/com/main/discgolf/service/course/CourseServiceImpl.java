package com.main.discgolf.service.course;

import com.main.discgolf.model.Course;
import com.main.discgolf.model.CourseByRound;
import com.main.discgolf.model.Hole;
import com.main.discgolf.model.Round;
import com.main.discgolf.repository.CourseRepository;
import com.main.discgolf.repository.RoundRepository;
import com.main.discgolf.service.round.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private RoundService roundService;

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = (List<Course>) this.courseRepository.findAll();
        for (Course course : courses) {
            double average = getCourseAverageByCourseId(course.getId());
            int numberOfTimesPlayed = roundRepository.findAllRoundsByCourseId(course.getId()).size();
            course.setCourseAverage(average);
            course.setNumberOfTimesPlayed(numberOfTimesPlayed);
        }
        return courses;
    }

    @Override
    public Course getCourseById(Long id) {
        Optional<Course> optional = courseRepository.findById(id);
        Course course;
        if (optional.isPresent()) {
            course = optional.get();
        } else {
            throw new RuntimeException("Course not found for id :: " + id);
        }
        return course;
    }

    @Override
    public Course getCourseByName(String name) {
        List<Course> courses = getAllCourses();
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }
        return null;
    }

    @Override
    public Course saveCourse(Course course) {
        return this.courseRepository.save(course);
    }

    @Override
    public void deleteCourseById(Long id) {
        this.courseRepository.deleteById(id);
    }

    @Override
    public List<Integer> getCourseListOfHolePars(Course course) {
        List<Integer> parList = new ArrayList<>();
        for (Hole hole : course.getHoles()) {
            parList.add(hole.getPar());
        }
        return parList;
    }

    @Override
    public double getCourseAverageByCourseId(Long courseId) {
        List<Round> rounds = roundRepository.findAllRoundsByCourseId(courseId);
        return rounds.stream().filter(r -> r.getCourse().getId().equals(courseId)).collect(Collectors.averagingDouble(Round::getTotal));
    }

    @Override
    public List<CourseByRound> getListOfCourseByRoundByUserId(Long userId) {
        List<CourseByRound> courseByRounds = new ArrayList<>();
        List<Course> courses = courseRepository.findAllCoursesAUserHasPlayedByUserId(userId);

        for (Course course : courses) {
            CourseByRound courseByRound = new CourseByRound();
            courseByRound.setCourseId(course.getId());
            courseByRound.setCourseName(course.getName());
            courseByRound.setCoursePar(course.getPar());
            courseByRound.setCourseRecord(course.getRecord());
            courseByRound.setCourseAverage(course.getCourseAverage());
            List<Round> rounds = roundRepository.findAllRoundsByUserIdAndCourseId(userId, course.getId());

            rounds.sort(Comparator.comparing(Round::getRoundDate).reversed());
            courseByRound.setRounds(rounds);
            for (Round round : courseByRound.getRounds()) {
                round.setBarChartArray(roundService.getListOfScoresByRoundId(round.getRoundId()));
            }
            courseByRound.setTimesPlayed(courseByRound.getRounds().size());
            courseByRounds.add(courseByRound);

        }
        courseByRounds.sort(Comparator.comparing(CourseByRound::getCourseName));
        return courseByRounds;
    }


}
