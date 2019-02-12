package com.example.cs5610spring2019assignment5serverjava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class ModuleService {

	@Autowired
	CourseService cs;

	@PostMapping("/api/courses/{cid}/modules")
	public Module createCourse(
			@PathVariable("cid") int cid,
			@RequestBody Module module) {

		Course course = cs.findCourseById(cid);

		if(course!=null) {
			module.setId(IdGenerator.generateId(ModuleService.class));
			if(course.getModules()==null)
				course.setModules(new ArrayList<>());
			course.getModules().add(module);
			return module;
		}
		return null;
	}

	@GetMapping("/api/courses/{cid}/modules")
	public List<Module> findAllModules(@PathVariable("cid") int cid){
		Course course = cs.findCourseById(cid);
		if(course!=null) {
			return course.getModules();
		}
		return null;
	}

	@GetMapping("/api/modules/{mid}")
	public Module findModuleById(@PathVariable("mid") int mid){
		List<Course> courses = cs.findAllCourses();
		if(courses==null)
			return null;
		for(Course course: courses) {
			List<Module> modules= course.getModules();
			if(modules==null) continue;
			Module module = modules.stream()
					.filter(m -> m.getId() == mid)
					.findAny()
					.orElse(null);
			if(module!=null)
				return module;
		}
		return null;
	}

	@PutMapping("/api/modules/{mid}")
	public Module updateModule(@PathVariable("mid") int mid, 
			@RequestBody Module module) {
		Module mod= findModuleById(mid);
		if(mod!=null) {
			mod.setLessons(module.getLessons());
			mod.setTitle(module.getTitle());
			return mod;
		}
		return null;
	}

	@DeleteMapping("/api/modules/{mid}")
	public List<Module> deleteModule
	(@PathVariable("mid") int mid) {

		List<Course> courses = cs.findAllCourses();
		if(courses==null)
			return null;

		for(Course course: courses) {
			List<Module> modules= course.getModules();
			if(modules==null) continue;
			Module module = modules.stream()
					.filter(m -> m.getId() == mid)
					.findAny()
					.orElse(null);
			if(module!=null) {
				course.getModules().remove(module);
				return course.getModules();
			}
		}

		return null;
	}
}
