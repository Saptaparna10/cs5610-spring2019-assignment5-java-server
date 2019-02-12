package com.example.cs5610spring2019assignment5serverjava.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs5610spring2019assignment5serverjava.models.Course;

@RestController
public class CourseService {

	List<Course> courses = new ArrayList<Course>();

	@PostMapping("/api/courses")
	public List<Course> createCourse(
			@RequestBody Course course) {
		course.setId(IdGenerator.generateId(CourseService.class));
		courses.add(course);
		return courses;
	}

	@GetMapping("/api/courses")
	public List<Course> findAllCourses(){
		return courses;
	}

	@GetMapping("/api/courses/{id}")
	Course findCourseById(@PathVariable("id") int id) {
		for(Course c : courses)
			if(c.getId() == id)
				return c;
		return null;

	}

	@DeleteMapping("/api/courses/{id}")
	public List<Course> deleteCourse
	(@PathVariable("id") int courseId) {
		courses = courses.stream()
				.filter(course -> course.getId() != courseId)
				.collect(Collectors.toList());
		return courses;
	}
	
	@PutMapping("/api/courses/{id}")
	public Course updateCourse(@PathVariable("id") int cid, @RequestBody Course course) {
		
		Course c = findCourseById(cid);
		
		if(c!=null) {
			c.setTitle(course.getTitle());
			c.setModules(course.getModules());
			return c;
		}
        
        return null;
	}

}
