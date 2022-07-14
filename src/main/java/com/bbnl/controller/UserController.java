package com.bbnl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bbnl.entity.User;
import com.bbnl.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	public BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String HomePage() {
		return "home";
	}
	
	@GetMapping("/user")
	public String userDashboard(Model model) {
		List<User> listuser = service.listAllUser();
		model.addAttribute("listuser", listuser);
		return "userdashboard";
	}
	
	@RequestMapping(value = "/signup")
	public String signupUser(Model model) {
		model.addAttribute("user", new User());
		return "userSignup";
	}
	
	@RequestMapping(value = "/saveuser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") User user, Model model) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		service.saveUser(user);
		model.addAttribute("register", "Registration Sucessfull !!");
		return "redirect:/signin";
	}
	
	@GetMapping("/signin")
	public String signinUser(Model model) {
		return "userSignin";
	}
}
