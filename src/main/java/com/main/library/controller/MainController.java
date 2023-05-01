package com.main.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.main.discgolf.model.Round;
import com.main.discgolf.model.Score;
import com.main.discgolf.model.UserInfo;
import com.main.discgolf.service.userInfo.UserInfoService;
import com.main.library.model.User;
import com.main.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigInteger;
import java.security.Principal;
import java.util.*;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserInfoService userInfoService;
	
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
		List<UserInfo> userInfoList = userInfoService.getListOfUserInfoByListOfUser(users);
		System.out.println("User info: " + userInfoList);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		model.addAttribute("userId", id);
		model.addAttribute("users", userInfoList);
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
