package com.main.discgolf.service.course;

import com.main.discgolf.model.Course;
import com.main.discgolf.model.Hole;
import com.main.discgolf.repository.CourseRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CourseServiceImplTest {

    @InjectMocks
    CourseServiceImpl courseServiceImpl;

    @Mock
    CourseRepository courseRepository;



    @Mock
    Course course;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void getAllCourses() {
        Hole testHole1 = new Hole(1L, 1, 3);
        Hole testHole2 = new Hole(2L, 3, 4);
        List<Hole> testHoles = new ArrayList<>();
        testHoles.add(testHole1);
        testHoles.add(testHole2);

        List<Course> testCourses = new ArrayList<>();

        Course testCourse1 = new Course(1L, "test course 1", testHoles, 7, 5, 6.5, 3);
        Course testCourse2 = new Course(2L, "test course 2", testHoles, 7, 6, 7.0, 4);
        Course testCourse3 = new Course(3L, "test course 3", testHoles, 7, 7, 7, 5);


        testCourses.add(testCourse1);
        testCourses.add(testCourse2);
        testCourses.add(testCourse3);

        when(courseServiceImpl.getAllCourses()).thenReturn(testCourses);

        List<Course> courses = courseServiceImpl.getAllCourses();

        assertEquals(3, courses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void getCourseById() {
        Hole testHole1 = new Hole(1L, 1, 3);
        Hole testHole2 = new Hole(2L, 3, 4);
        List<Hole> testHoles = new ArrayList<>();
        testHoles.add(testHole1);
        testHoles.add(testHole2);

        Course testCourse = new Course(1L, "test course", testHoles, 7, 5, 6.5, 3);
        when(course.getId()).thenReturn(testCourse.getId());
        assertEquals(1L, testCourse.getId());
    }

    @Test
    void getCourseByName() {
        Hole testHole1 = new Hole(1L, 1, 3);
        Hole testHole2 = new Hole(2L, 3, 4);
        List<Hole> testHoles = new ArrayList<>();
        testHoles.add(testHole1);
        testHoles.add(testHole2);

        Course testCourse = new Course(1L, "test course", testHoles, 7, 5, 6.5, 3);
        when(course.getName()).thenReturn(testCourse.getName());
        assertEquals("test course", testCourse.getName());
    }

    @Test
    void saveCourse() {
        Course course = new Course();
        courseServiceImpl.saveCourse(course);
        verify(courseRepository, times(1)).save(any());

        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(captor.capture());
        assertEquals(captor.getValue().getName(), course.getName());
    }

    @Test
    void deleteCourseById() {
        Course course = new Course();
        courseServiceImpl.saveCourse(course);
        courseServiceImpl.deleteCourseById(course.getId());
        verify(courseRepository, times(1)).deleteById(course.getId());
        assertNull(course.getId());
    }

    @Test
    void getCourseListOfHolePars() {
        Hole testHole1 = new Hole(1L, 1, 3);
        Hole testHole2 = new Hole(2L, 3, 4);
        List<Hole> testHoles = new ArrayList<>();
        testHoles.add(testHole1);
        testHoles.add(testHole2);
        Course testCourse = new Course(1L, "test course", testHoles, 7, 5, 6.5, 3);
        List<Integer> holeList = courseServiceImpl.getCourseListOfHolePars(testCourse);

        assertEquals(holeList.size(), 2);

    }
}