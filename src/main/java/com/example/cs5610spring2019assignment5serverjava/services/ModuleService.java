package com.example.cs5610spring2019assignment5serverjava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs5610spring2019assignment5serverjava.models.Course;
import com.example.cs5610spring2019assignment5serverjava.models.Module;
import com.example.cs5610spring2019assignment5serverjava.models.Person;
import com.example.cs5610spring2019assignment5serverjava.repositories.ModuleRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
public class ModuleService {

	@Autowired
	CourseService cs;
	
	@Autowired
	ModuleRepository repository;

	Person currentUser;
	
	@PostMapping("/api/courses/{cid}/modules")
	public Module createModule(
			HttpSession session,
			@PathVariable("cid") int cid,
			@RequestBody Module module) {

		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
		Course course = cs.findCourseById(session, cid);

		if(course!=null) {
//			module.setId(IdGenerator.generateId(ModuleService.class));
//			if(module.getLessons()==null)
//				module.setLessons(new ArrayList<>());
//			course.getModules().add(module);
			module.setCourse(course);
			return repository.save(module);
		}
		return null;
	}

	@GetMapping("/api/courses/{cid}/modules")
	public List<Module> findAllModules(HttpSession session, @PathVariable("cid") int cid){
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
		Course course = cs.findCourseById(session, cid);
		if(course!=null) {
			return course.getModules();
		}
		return null;
	}

	@GetMapping("/api/modules/{mid}")
	public Module findModuleById(HttpSession session, @PathVariable("mid") int mid){
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
//		List<Course> courses = cs.findAllCourses(session);
//		if(courses==null)
//			return null;
//		for(Course course: courses) {
//			List<Module> modules= course.getModules();
//			if(modules==null) continue;
//			Module module = modules.stream()
//					.filter(m -> m.getId() == mid)
//					.findAny()
//					.orElse(null);
//			if(module!=null)
//				return module;
//		}
		
		Optional<Module> op = repository.findById(mid);
		Module module = null;
		if (op.isPresent())
			module = op.get();
		return module;
	}

	@PutMapping("/api/modules/{mid}")
	public Module updateModule(HttpSession session, @PathVariable("mid") int mid, 
			@RequestBody Module module) {
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
//		Module mod= findModuleById(session, mid);
//		if(mod!=null) {
//			mod.setLessons(module.getLessons());
//			mod.setTitle(module.getTitle());
//			return mod;
//		}
//		return null;
		Module temp = findModuleById(session, mid);
		temp.setTitle(module.getTitle());
		return repository.save(temp);
	}

	@DeleteMapping("/api/modules/{mid}")
	public List<Module> deleteModule
	(HttpSession session, @PathVariable("mid") int mid) {
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;

//		List<Course> courses = cs.findAllCourses(session);
//		if(courses==null)
//			return null;
//
//		for(Course course: courses) {
//			List<Module> modules= course.getModules();
//			if(modules==null) continue;
//			Module module = modules.stream()
//					.filter(m -> m.getId() == mid)
//					.findAny()
//					.orElse(null);
//			if(module!=null) {
//				course.getModules().remove(module);
//				return course.getModules();
//			}
//		}
//
//		return null;
		repository.deleteById(mid);
		return findAllModules(session, findModuleById(session, mid).getCourse().getId());
	}
}
