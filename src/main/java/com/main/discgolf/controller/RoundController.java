package com.main.discgolf.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.main.discgolf.model.Course;
import com.main.discgolf.model.Round;
import com.main.discgolf.model.RoundArray;
import com.main.discgolf.repository.RoundRepository;
import com.main.discgolf.repository.ScoreRepository;
import com.main.discgolf.service.course.CourseService;
import com.main.discgolf.service.round.RoundService;
import com.main.library.model.User;
import com.main.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/discgolf")
public class RoundController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CourseService courseService;
    @Autowired
    private RoundRepository roundRepository;

    @GetMapping("/rounds/{id}")
    public String roundsHome(@PathVariable(value = "id") Long id,
                             Model model) {
        List<Course> courses = courseService.getAllCourses();
        List<Round> rounds = userService.getUserById(id).getRounds();

        rounds.sort(Comparator.comparing(Round::getRoundDate).reversed());
//        Map<Course, List<Round>> mapRoundsByCourse = rounds.stream().collect(Collectors.groupingBy(Round::getCourse));
//        List<RoundArray> roundArrays = roundService.getListOfRoundArrays();



        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Map<Course, List<Round>> mapRoundsByCourse = rounds.stream().collect(Collectors.groupingBy(Round::getCourse));
        JsonNode jsonNode = mapper.valueToTree(mapRoundsByCourse);
        model.addAttribute("roundsJson", jsonNode);


        model.addAttribute("userId", id);
        model.addAttribute("roundService", roundService);
        model.addAttribute("courses", courses);
        model.addAttribute("rounds", mapRoundsByCourse);
//        model.addAttribute("roundArrays", roundArrays);
        return "/discgolf/round/rounds";
    }

    @GetMapping("/newRound")
    public String showNewRoundForm(@RequestParam("course") String course,
                                   Model model, Principal principal) {

        Round round = new Round();
        Course selectedCourse = courseService.getCourseByName(course);
        System.out.println("Course " + selectedCourse.toString());
        model.addAttribute("userId", userService.getUserIdByPrincipalName(principal));
        model.addAttribute("newRound", round);
        model.addAttribute("course", selectedCourse);

        return "/discgolf/round/newRound";
    }

    @PostMapping("/saveRoundForCourse/{id}")
    public String saveRound(@PathVariable("id") long id,
                            @RequestParam("roundDate") String date,
                            @RequestParam("scoreValues")List<Integer> scores,
                            Principal principal) throws ParseException {

        Course course = courseService.getCourseById(id);

        Round round = roundService.addDateAndScoresToRound(date, scores, course);

        User user = userService.getUserById(userService.getUserIdByPrincipalName(principal));
        user.addRound(round);
        roundService.saveRound(round);
        return "redirect:/discgolf/rounds/" + user.getId();
    }

    @GetMapping("/deleteRound/{id}")
    public String deleteRoundById(@PathVariable(value = "id") long id, Principal principal) {
        Long userId = userService.getUserIdByPrincipalName(principal);
        this.roundService.deleteRoundById(id);
        return "redirect:/discgolf/rounds/" + userId;
    }


}
