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
import com.example.cs5610spring2019assignment5serverjava.repositories.PersonRepository;

@RestController
@CrossOrigin(origins = "https://das-saptaparna-assignment5.herokuapp.com", allowCredentials="true") 
public class UserService {

	//List<Person> users = new ArrayList<Person>();
	@Autowired
	PersonRepository repository;

	@PostMapping("/api/register")
	public Person register(@RequestBody Person newUser,
			HttpSession session) {
		
		List<Person> users= findAllUsers();
		
		for (Person user : users) {
			if(user.getUsername().equals(newUser.getUsername())) {
				return null;
			}
		}
		//newUser.setId(IdGenerator.generateId(CourseService.class));
		//newUser.setCourses(new ArrayList<>());
		//users.add(newUser);
		return repository.save(newUser);
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
		
		List<Person> users= findAllUsers();
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
		return (List<Person>) repository.findAll();
	}

	@GetMapping("/api/users/{id}")
	Person findUserById(@PathVariable("id") int id) {
		
//		List<Person> users= findAllUsers();
//		for(Person user : users)
//			if(user.getId() == id)
//				return user;
//		return null;
		
		Optional<Person> op = repository.findById(id);
		Person user = null;
		if (op.isPresent())
			user = op.get();
		return user;
	}

}
