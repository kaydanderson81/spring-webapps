package com.main.discgolf.service.course;

import com.main.discgolf.model.Course;
import com.main.discgolf.model.CourseByRound;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();

    Course getCourseById(Long id);

    Course getCourseByName(String name);

    Course saveCourse(Course course);

    void deleteCourseById(Long id);

    List<Integer> getCourseListOfHolePars(Course course);

    double getCourseAverageByCourseId(Long id);

    List<CourseByRound> getListOfCourseByRoundByUserId(Long userId);
}
