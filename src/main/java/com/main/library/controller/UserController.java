package com.main.library.controller;

import com.main.library.model.Role;
import com.main.library.model.User;
import com.main.library.repository.UserRepository;
import com.main.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/library")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
//	@GetMapping("/users")
//	public String showUserPage(Model model) {
//		model.addAttribute("listOfUsers", userService.getAllUsers());
//		return "/library/user/users";
////		return findPaginated(1, "asc", "firstName", model); // returns a view, 1 is default No pages, default asc ascending order by firstName
//	}

	@GetMapping("/users")
	public String getAllPages(Model model) {
		return getOnePage(model, 1);
	}

	@GetMapping("/users/page/{pageNo}")
	public String getOnePage(Model model, @PathVariable("pageNo") int currentPage) {
		Page<User> page = userService.findPaginatedUsers(currentPage);
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<User> users = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("listOfUsers", users);
		return "/library/user/users";
	}

	@GetMapping("/users/page/{pageNo}/{field}")
	public String getAllWithSort(Model model,
								 @PathVariable("pageNo") int currentPage,
								 @PathVariable String field,
								 @PathParam("sortDir") String sortDir) {
		Page<User> page = userService.findAllWithSort(field, sortDir, currentPage);
		List<User> users = page.getContent();
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);

		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");
		model.addAttribute("users", users);
		return "/library/user/users";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute(name = "user") User user,
							@RequestParam(name = "mainImage", required = false) MultipartFile multipartFile) throws IOException {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		user.setUserImage(fileName);
		//save user to database
		User savedUser = userService.saveUser(user);
		String uploadDir = "./user-images/" + savedUser.getId();
		Path uploadPath = Paths.get(uploadDir);
		//check path doesn't exist
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		//read inputStream from the MultifilePath to store the file index
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new IOException("Could not save the upload file: " + fileName);
		}	
		
		return "redirect:/library/users";
	}
	
	
	@GetMapping("/updateUser/{id}")
	public String showFormForUpdateUser(@PathVariable(value = "id") Long id, Model model) {
		User user = userService.getUserById(id);
		List<Role> listRoles = userService.getRoles();
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		return "/library/user/updateUser";
		
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable (value = "id") Long id) {
		//call delete user method
		this.userService.deleteUserById(id);
		return "redirect:/library/users";
	}
	
	@RequestMapping(value = "cancel", method = RequestMethod.GET)
	public String cancelUpdateUser() {
	    return "redirect:/library/users";
	}

}
