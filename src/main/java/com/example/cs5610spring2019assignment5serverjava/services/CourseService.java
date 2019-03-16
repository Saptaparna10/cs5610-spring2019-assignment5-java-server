package com.example.cs5610spring2019assignment5serverjava.services;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs5610spring2019assignment5serverjava.models.Course;
import com.example.cs5610spring2019assignment5serverjava.models.Person;
import com.example.cs5610spring2019assignment5serverjava.repositories.CourseRepository;
import com.example.cs5610spring2019assignment5serverjava.repositories.PersonRepository;

//https://glacial-gorge-34114.herokuapp.com, 
@RestController
@CrossOrigin(origins = "https://das-saptaparna-assignment5.herokuapp.com", allowCredentials="true") 
public class CourseService {

	@Autowired
	UserService us;
	
	@Autowired
	CourseRepository repository;
	
	@Autowired
	PersonRepository prepository;

	Person currentUser;

	@PostMapping("/api/courses")
	public List<Course> createCourse(
			HttpSession session,
			@RequestBody Course course) {
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
//		course.setId(IdGenerator.generateId(CourseService.class));
//		if(course.getModules()==null)
//			course.setModules(new ArrayList<>());
//		currentUser.getCourses().add(course);
		course.setAuthor(currentUser);
		repository.save(course);
		
		return prepository.findById(currentUser.getId()).get().getCourses();
		
	}

	@GetMapping("/api/courses")
	public List<Course> findAllCourses(HttpSession session){
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser!=null)
			return currentUser.getCourses();
		return null;
	}

	@GetMapping("/api/courses/{id}")
	Course findCourseById(HttpSession session, @PathVariable("id") int id) {
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
//		for(Course c : currentUser.getCourses())
//			if(c.getId() == id)
//				return c;
		Optional<Course> op = repository.findById(id);
		Course course = null;
		if (op.isPresent())
			course = op.get();
		return course;

	}

	@DeleteMapping("/api/courses/{id}")
	public List<Course> deleteCourse
	(HttpSession session, @PathVariable("id") int courseId) {
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
//		currentUser.setCourses(currentUser.getCourses().stream()
//				.filter(course -> course.getId() != courseId)
//				.collect(Collectors.toList()));
		
		repository.deleteById(courseId);
		return  prepository.findById(currentUser.getId()).get().getCourses();
	}

	@PutMapping("/api/courses/{id}")
	public Course updateCourse(HttpSession session, @PathVariable("id") int cid, @RequestBody Course course) {
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
//		Course c = findCourseById(session, cid);
//
//		if(c!=null) {
//			c.setTitle(course.getTitle());
//			c.setModules(course.getModules());
//			return c;
//		}
//
//		return null;
		
		Course c = findCourseById(session, cid);
		if(c!=null) {
			c.setTitle(course.getTitle());
			//c.setModules(course.getModules());
		}
		
		repository.save(c);
		return c;
	}

}
