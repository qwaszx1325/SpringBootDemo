package com.competnion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.competnion.util.StendMail;

@RestController
public class Demo {
	@Autowired
	private StendMail stendMail;

	@GetMapping("/hello")
	public String demo() {
		stendMail.sendSimpleEmail("qwaszx1325@gmail.com", "Test Subject", "This is a test email.");

		return "Hello";
	}
}
