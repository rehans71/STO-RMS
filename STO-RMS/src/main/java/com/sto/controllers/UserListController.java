package com.sto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sto.repositories.UserRepository;
import com.sto.service.UserDetailsServiceImpl;

@Controller
public class UserListController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getUserList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		model.addAttribute("currentUser", userDetailsServiceImpl.getCurrentUser());
		return "user/list";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login", params = "error", method = RequestMethod.GET)
	public String loginError(Model model) {
		model.addAttribute("error", "true");
		return "login";
	}
}