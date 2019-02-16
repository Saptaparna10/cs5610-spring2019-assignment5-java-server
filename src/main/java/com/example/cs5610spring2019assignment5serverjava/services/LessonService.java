package com.example.cs5610spring2019assignment5serverjava.services;

import java.util.ArrayList;
import java.util.List;

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
import com.example.cs5610spring2019assignment5serverjava.models.Lesson;
import com.example.cs5610spring2019assignment5serverjava.models.Module;
import com.example.cs5610spring2019assignment5serverjava.models.Person;

@RestController
@CrossOrigin(origins = "https://glacial-gorge-34114.herokuapp.com", allowCredentials="true")
public class LessonService {
	
	@Autowired
	ModuleService ms;
	
	@Autowired
	CourseService cs;
	
	Person currentUser;
	
	@PostMapping("/api/modules/{mid}/lessons")
	public Lesson createLesson(
			HttpSession session,
			@PathVariable("mid") int mid,
			@RequestBody Lesson lesson) {
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;

		Module module = ms.findModuleById(session, mid);

		if(module!=null) {
			lesson.setId(IdGenerator.generateId(LessonService.class));
			if(lesson.getTopics()==null)
				lesson.setTopics(new ArrayList<>());
			module.getLessons().add(lesson);
			return lesson;
		}
		return null;
	}
	
	@GetMapping("/api/modules/{mid}/lessons")
	public List<Lesson> findAllLessons(HttpSession session, @PathVariable("mid") int mid){
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
		Module module = ms.findModuleById(session, mid);
		if(module!=null) {
			return module.getLessons();
		}
		return null;
	}

	@GetMapping("/api/lessons/{lid}")
	public Lesson findLessonById(HttpSession session, @PathVariable("lid") int lid){
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
		List<Course> courses = cs.findAllCourses(session);
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
	public Lesson updateLesson(HttpSession session, @PathVariable("lid") int lid, 
			@RequestBody Lesson lesson) {
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
		Lesson les= findLessonById(session, lid);
		if(les!=null) {
			les.setTopics(lesson.getTopics());
			les.setTitle(lesson.getTitle());
			return les;
		}
		return null;
	}

	@DeleteMapping("/api/lessons/{lid}")
	public List<Lesson> deleteLesson
	(HttpSession session, @PathVariable("lid") int lid) {
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;

		List<Course> courses = cs.findAllCourses(session);
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
