package com.example.cs5610spring2019assignment5serverjava.services;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs5610spring2019assignment5serverjava.models.Person;

@RestController
@CrossOrigin(origins = "https://damp-earth-67754.herokuapp.com", allowCredentials="true") 
public class UserService {

	List<Person> users = new ArrayList<Person>();

	@PostMapping("/api/register")
	public Person register(@RequestBody Person newUser,
			HttpSession session) {
		
		for (Person user : users) {
			if(user.getUsername().equals(newUser.getUsername())) {
				return null;
			}
		}
		newUser.setId(IdGenerator.generateId(CourseService.class));
		newUser.setCourses(new ArrayList<>());
		users.add(newUser);
		return newUser;
	}

	@GetMapping("/api/profile")
	public Person profile(HttpSession session) {
		Person currentUser = (Person) session.getAttribute("currentUser");	
		return currentUser;
	}

	@PostMapping("/api/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@PostMapping("/api/login")
	public Person login(	@RequestBody Person credentials,
			HttpSession session) {
		for (Person user : users) {
			if( user.getUsername().equals(credentials.getUsername())
					&& user.getPassword().equals(credentials.getPassword())) {
				session.setAttribute("currentUser", user);
				return user;
			}
		}
		return null;
	}

	@GetMapping("/api/users")
	List<Person> findAllUsers() {
		return users;
	}

	@GetMapping("/api/users/{id}")
	Person findUserById(@PathVariable("id") int id) {
		for(Person user : users)
			if(user.getId() == id)
				return user;
		return null;

	}

}
