package com.main.discgolf.repository;

import com.main.discgolf.model.Course;
import com.main.discgolf.model.Hole;
import com.main.discgolf.service.course.CourseServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseRepositoryTest {

    @InjectMocks
    CourseServiceImpl courseServiceImpl;

    @Mock
    Course course;

    @Mock
    private CourseRepository courseRepository;



    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCourseById() {
        Hole testHole1 = new Hole(1L, 1, 3);
        Hole testHole2 = new Hole(2L, 3, 4);
        List<Hole> testHoles = new ArrayList<>();
        testHoles.add(testHole1);
        testHoles.add(testHole2);

        Course testCourse = new Course(1L, "test course", testHoles, 7, 5, 6.5);
        when(course.getId()).thenReturn(testCourse.getId());

        assertEquals("test course", testCourse.getName());
        assertEquals(7, testCourse.getPar());
        assertEquals(5, testCourse.getRecord());
    }

}