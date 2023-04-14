package com.main.discgolf.service.course;

import com.main.discgolf.model.Course;
import com.main.discgolf.model.Hole;
import com.main.discgolf.model.Round;
import com.main.discgolf.repository.CourseRepository;
import com.main.discgolf.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Override
    public List<Course> getAllCourses() {
        return (List<Course>) this.courseRepository.findAll();
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
        double average = getCourseAverageByCourseId(course.getId());
        course.setCourseAverage(average);
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


}
