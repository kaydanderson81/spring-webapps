package com.main.library.controller;

import com.main.discgolf.model.Round;
import com.main.discgolf.model.UserInfo;
import com.main.library.model.User;
import com.main.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.*;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/home")
	public String homeMain() {
		return "home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/library")
	public String userHome() {
		return "/library/libraryHome";
	}

	@GetMapping("/discgolf")
	public String discGolfHome(Model model, Principal principal) {
		String name = principal.getName();
		long id = getUserIdByUserName(name);
		List<User> users = userService.getAllUsers();


		List<UserInfo> userInfos = new ArrayList<>();
		for (User user : users) {
			List<Round> rounds = userService.getUserById(user.getId()).getRounds();
//			List<Integer> bestRounds = new ArrayList<>();
			int numHoles = 0;
			for (Round round : rounds) {
				int holesPlayed = round.getScores().size();
				numHoles += holesPlayed;
			}


//			for (int i=0; i< rounds.size();i++) {
//				bestRounds.add(Collections.min(rounds.get(i).getTotal()));
//			}



			UserInfo newUserInfo = new UserInfo();
			newUserInfo.setUserId(user.getId());
			newUserInfo.setName(user.getFirstName());
			newUserInfo.setRoundsPlayed(rounds.size());
			newUserInfo.setHoles(numHoles);
			newUserInfo.setPlayerAverage(userService.getAverageScoreByUser(user.getId()));
			newUserInfo.setAces(userService.getNumberOfAces(user.getId()));
//			newUserInfo.setBestRound();
			userInfos.add(newUserInfo);
		}


		System.out.println("User info: " + userInfos);

		model.addAttribute("userId", id);
		model.addAttribute("users", userInfos);
		return "/discgolf/discGolfHome";
	}


	private Long getUserIdByUserName(String name) {
		List<User> users = userService.getAllUsers();
		for (User user : users) {
			if (user.getEmail().equals(name)) {
				return user.getId();
			}
		}
		return null;
	}
	
	
}
