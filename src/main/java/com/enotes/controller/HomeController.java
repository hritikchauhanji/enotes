package com.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import com.enotes.service.UserService;

public class HomeController {

	@Autowired
	private UserService userService;
	
}
