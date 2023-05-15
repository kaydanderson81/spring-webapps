package com.main.discgolf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.main.discgolf.model.Course;
import com.main.discgolf.model.CourseByRound;
import com.main.discgolf.model.Round;
import com.main.discgolf.service.course.CourseService;
import com.main.discgolf.service.round.RoundService;
import com.main.library.model.User;
import com.main.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/discgolf")
public class RoundController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private CourseService courseService;

    public RoundController() {
    }

    @GetMapping("/rounds/{id}")
    public String roundsHome(@PathVariable(value = "id") Long id,
                             Model model) {
        List<Course> courses = courseService.getAllCourses(); //for drop down list
        courses.sort(Comparator.comparing(Course::getName));
        List<CourseByRound> courseByRounds = courseService.getListOfCourseByRoundByUserId(id); //for userRounds

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        model.addAttribute("courses", courses);
        model.addAttribute("roundsJsonNode", roundService.listOfRoundsByCourseByRound(courseByRounds));
        model.addAttribute("userId", id);
        model.addAttribute("roundService", roundService);
        model.addAttribute("courseByRounds", courseByRounds);

        return "/discgolf/round/rounds";
    }

    @GetMapping("/newRound")
    public String showNewRoundForm(@RequestParam("course") String course,
                                   Model model, Principal principal) {

        Round round = new Round();
        Course selectedCourse = courseService.getCourseByName(course);
        model.addAttribute("userId", userService.getUserIdByPrincipalName(principal));
        model.addAttribute("newRound", round);
        model.addAttribute("course", selectedCourse);

        return "/discgolf/round/newRound";
    }

    @PostMapping("/saveRoundForCourse/{id}")
    public String saveRound(@PathVariable("id") long id,
                            @RequestParam("roundDate") String date,
                            @RequestParam("scoreValues")List<Integer> scores,
                            Principal principal, RedirectAttributes redirectAttributes) throws ParseException {

        Course course = courseService.getCourseById(id);

        Round round = roundService.addDateAndScoresToRound(date, scores, course);

        User user = userService.getUserById(userService.getUserIdByPrincipalName(principal));
        user.addRound(round);
        roundService.saveRound(round);
        redirectAttributes.addAttribute("updatedCourseId", course.getId());
        return "redirect:/discgolf/rounds/" + user.getId();
    }

    @GetMapping("/deleteRound/{id}")
    public String deleteRoundById(@PathVariable(value = "id") long id, Principal principal, RedirectAttributes redirectAttributes) {
        Long userId = userService.getUserIdByPrincipalName(principal);
        Long courseId = roundService.getRoundById(id).getCourse().getId();
        this.roundService.deleteRoundById(id);
        redirectAttributes.addAttribute("updatedCourseId", courseId);
        return "redirect:/discgolf/rounds/" + userId;
    }


}
