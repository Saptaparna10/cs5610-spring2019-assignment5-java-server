package com.example.cs5610spring2019assignment5serverjava.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs5610spring2019assignment5serverjava.models.Course;
import com.example.cs5610spring2019assignment5serverjava.models.Lesson;
import com.example.cs5610spring2019assignment5serverjava.models.Module;

@RestController
public class LessonService {
	
	@Autowired
	ModuleService ms;
	
	@Autowired
	CourseService cs;
	
	@PostMapping("/api/modules/{mid}/lessons")
	public Lesson createLesson(
			@PathVariable("mid") int mid,
			@RequestBody Lesson lesson) {

		Module module = ms.findModuleById(mid);

		if(module!=null) {
			lesson.setId(IdGenerator.generateId(LessonService.class));
			if(module.getLessons()==null)
				module.setLessons(new ArrayList<>());
			module.getLessons().add(lesson);
			return lesson;
		}
		return null;
	}
	
	@GetMapping("/api/modules/{mid}/lessons")
	public List<Lesson> findAllLessons(@PathVariable("mid") int mid){
		Module module = ms.findModuleById(mid);
		if(module!=null) {
			return module.getLessons();
		}
		return null;
	}

	@GetMapping("/api/lessons/{lid}")
	public Lesson findLessonById(@PathVariable("lid") int lid){
		List<Course> courses = cs.findAllCourses();
		if(courses==null)
			return null;
		
		for(Course course: courses) {
			for(Module module: course.getModules()) {
				for(Lesson lesson: module.getLessons()) {
					if(lesson.getId()==lid)
						return lesson;
				}
			}
		}
		return null;
	}
	
	@PutMapping("/api/lessons/{lid}")
	public Lesson updateLesson(@PathVariable("lid") int lid, 
			@RequestBody Lesson lesson) {
		Lesson les= findLessonById(lid);
		if(les!=null) {
			les.setTopics(lesson.getTopics());
			les.setTitle(lesson.getTitle());
			return les;
		}
		return null;
	}

	@DeleteMapping("/api/lessons/{lid}")
	public List<Lesson> deleteLesson
	(@PathVariable("lid") int lid) {

		List<Course> courses = cs.findAllCourses();
		if(courses==null)
			return null;

		for(Course course: courses) {
			for(Module module: course.getModules()) {
				for(Lesson lesson: module.getLessons()) {
					if(lesson.getId()==lid) {
						module.getLessons().remove(lesson);
						return module.getLessons();
					}
				}
			}
		}

		return null;
	}

}
