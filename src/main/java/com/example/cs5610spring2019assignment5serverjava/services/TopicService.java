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
import com.example.cs5610spring2019assignment5serverjava.models.Topic;

@RestController
public class TopicService {
	
	@Autowired
	LessonService ls;
	
	@Autowired
	ModuleService ms;
	
	@Autowired
	CourseService cs;
	
	@PostMapping("/api/lessons/{lid}/topics")
	public Topic createTopic(
			@PathVariable("lid") int lid,
			@RequestBody Topic topic) {

		Lesson lesson = ls.findLessonById(lid);

		if(lesson!=null) {
			topic.setId(IdGenerator.generateId(TopicService.class));
			if(lesson.getTopics()==null)
				lesson.setTopics(new ArrayList<>());
			lesson.getTopics().add(topic);
			return topic;
		}
		return null;
	}
	
	@GetMapping("/api/lessons/{lid}/topics")
	public List<Topic> findAllTopics(@PathVariable("lid") int lid){
		Lesson lesson = ls.findLessonById(lid);
		if(lesson!=null) {
			return lesson.getTopics();
		}
		return null;
	}

	@GetMapping("/api/topics/{tid}")
	public Topic findTopicById(@PathVariable("tid") int tid){
		List<Course> courses = cs.findAllCourses();
		if(courses==null)
			return null;
		
		for(Course course: courses) {
			for(Module module: course.getModules()) {
				for(Lesson lesson: module.getLessons()) {
					for(Topic topic: lesson.getTopics()) {
						if(topic.getId()==tid)
							return topic;
					}
				}
			}
		}
		return null;
	}
	
	@PutMapping("/api/topics/{tid}")
	public Topic updateTopic(@PathVariable("tid") int tid, 
			@RequestBody Topic topic) {
		Topic top= findTopicById(tid);
		if(top!=null) {
			top.setWidgets(topic.getWidgets());
			top.setTitle(topic.getTitle());
			return top;
		}
		return null;
	}

	@DeleteMapping("/api/topics/{tid}")
	public List<Topic> deleteTopic
	(@PathVariable("tid") int tid) {

		List<Course> courses = cs.findAllCourses();
		if(courses==null)
			return null;

		for(Course course: courses) {
			for(Module module: course.getModules()) {
				for(Lesson lesson: module.getLessons()) {
					for(Topic topic: lesson.getTopics()) {
						if(topic.getId()==tid) {
							lesson.getTopics().remove(topic);
							return lesson.getTopics();
						}
					}
				}
			}
		}

		return null;
	}



}
