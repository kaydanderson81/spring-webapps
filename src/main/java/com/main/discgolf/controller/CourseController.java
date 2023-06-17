package com.main.discgolf.controller;

import com.main.discgolf.model.Course;
import com.main.discgolf.model.Hole;
import com.main.discgolf.repository.CourseRepository;
import com.main.discgolf.service.course.CourseService;
import com.main.library.controller.MainController;
import com.main.discgolf.service.hole.HoleService;
import com.main.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/discgolf")
public class CourseController extends MainController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private HoleService holeService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/courses")
    public String coursesHome(Model model) {
        List<Course> courses = this.courseService.getAllCourses();
        courses.sort(Comparator.comparing(Course::getNumberOfTimesPlayed).reversed());
        model.addAttribute("courses", courses);
        return "/discgolf/course/courses";
    }

    @GetMapping("/newCourseForm")
    public String showNewCourseForm(Model model) {
        Course newCourse = new Course();
        model.addAttribute("newCourse", newCourse);
        int[] array = IntStream.rangeClosed(1, 18).toArray();
        model.addAttribute("parList", array);
        return "/discgolf/course/newCourse";
    }

    @PostMapping("/saveCourse")
    public String saveCourse(@ModelAttribute("newCourse") Course course,
                             @RequestParam("parValues") List<Integer> parValues,
                             RedirectAttributes redirectAttributes) {
        parValues.removeAll(Arrays.asList("", null));

        List<Hole> holes = new ArrayList<>();
        int holeNum = 0;
        for (Integer parValue : parValues) {
            Hole hole = new Hole();
            holeNum++;
            hole.setNumber(holeNum);
            hole.setPar(parValue);
            holes.add(hole);
        }

        Integer sum = parValues.stream()
                .reduce(0, Integer::sum);
        course.setHoles(holes);
        course.setPar(sum);

        this.courseService.saveCourse(course);
        redirectAttributes.addAttribute("updatedCourseId", course.getId());
        return "redirect:/discgolf/courses";
    }

    @GetMapping("/showUpdateCourseForm/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        List<Hole> holeList = holeService.getAllHolesByCourseId(id);
        model.addAttribute("holeList", holeList);
        return "/discgolf/course/updateCourse";
    }

    @PostMapping("/updateCourse/{id}/parList/{parId}")
    public String updateCourse(@PathVariable( value = "id") long id,
                               @PathVariable( value = "parId") long parId,
                               @ModelAttribute Hole holeList,
                               RedirectAttributes redirectAttributes) {
        Course updateCourse = courseService.getCourseById(id);
        if (courseRepository.existsByName(updateCourse.getName())) {
            redirectAttributes.addFlashAttribute("failed", "Course name: " + updateCourse.getName() + " already exists");
            return "redirect:/discgolf/showUpdateCourseForm/" + id;
        }
        Hole oldHoleList = holeService.getHoleById(parId);
        holeService.deleteHoleById(oldHoleList.getHoleId());
        updateCourse.addHolesToCourse(holeList);
        this.courseService.saveCourse(updateCourse);
        redirectAttributes.addAttribute("updatedEmployeeId", updateCourse.getId());
        return "redirect:/discgolf/courses";
    }

    @GetMapping("/deleteCourse/{id}")
    public String deleteCourseById(@PathVariable(value = "id") long id) {
        this.courseService.deleteCourseById(id);
        return "redirect:/discgolf/courses";
    }
}
