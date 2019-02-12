package com.example.cs5610spring2019assignment5serverjava.services;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs5610spring2019assignment5serverjava.models.Person;

@RestController
public class UserService {
	
	List<Person> users = new ArrayList<Person>();
	
	@PostMapping("/api/register")
	public Person register(@RequestBody Person user,
	HttpSession session) {
		session.setAttribute("currentUser", user);
		users.add(user);
		return user;
	}
	
	@GetMapping("/api/profile")
	public Person profile(HttpSession session) {
		Person currentUser = (Person) session.getAttribute("currentUser");	
		return currentUser;
	}


}
