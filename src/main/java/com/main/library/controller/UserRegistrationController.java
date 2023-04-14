package com.main.library.controller;

import com.main.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.main.library.model.User;

import java.util.List;


@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
	
	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

	@ModelAttribute("user") //object in html form. Instead of doing this, we can also pass a Model parameter in the showRegistrationForm() method (53min in vid).
	public User user() {
		return new User();
	}

	//to handle the registration.html
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") User user) {
		List<User> users = userService.getAllUsers();
		for (User existingUser : users) {
			if (existingUser.getEmail().equals(user.getEmail())){
				return "redirect:/registration?error";
			}
		}
		userService.saveUser(user);
		return "redirect:/login?success"; //once user registration is successful we show a success message.
	}
	
}
