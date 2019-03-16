package com.example.cs5610spring2019assignment5serverjava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.example.cs5610spring2019assignment5serverjava.models.Topic;
import com.example.cs5610spring2019assignment5serverjava.models.Widget;
import com.example.cs5610spring2019assignment5serverjava.repositories.LessonRepository;
import com.example.cs5610spring2019assignment5serverjava.repositories.TopicRepository;

@RestController
@CrossOrigin(origins = "https://das-saptaparna-assignment5.herokuapp.com", allowCredentials="true")
public class TopicService {
	
	@Autowired
	LessonService ls;
	
	@Autowired
	ModuleService ms;
	
	@Autowired
	CourseService cs;
	
	@Autowired
	TopicRepository repository;
	
	Person currentUser;
	
	@PostMapping("/api/lessons/{lid}/topics")
	public Topic createTopic(
			HttpSession session,
			@PathVariable("lid") int lid,
			@RequestBody Topic topic) {
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;

		Lesson lesson = ls.findLessonById(session,lid);

		if(lesson!=null) {
			topic.setLesson(lesson);
			return repository.save(topic);
		}
		return null;
	}
	
	@GetMapping("/api/lessons/{lid}/topics")
	public List<Topic> findAllTopics(HttpSession session,@PathVariable("lid") int lid){
		
//		currentUser = (Person) session.getAttribute("currentUser");
//		if(currentUser==null) return null;
//		
//		Lesson lesson = ls.findLessonById(session, lid);
//		if(lesson!=null) {
//			return lesson.getTopics();
//		}
//		return null;
		Lesson lesson = ls.findLessonById(session, lid);
		if(lesson!=null) {
			return lesson.getTopics();
		}
		return null;
	}

	@GetMapping("/api/topics/{tid}")
	public Topic findTopicById(HttpSession session,@PathVariable("tid") int tid){
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
//		List<Course> courses = cs.findAllCourses(session);
//		if(courses==null)
//			return null;
//		
//		for(Course course: courses) {
//			for(Module module: course.getModules()) {
//				for(Lesson lesson: module.getLessons()) {
//					for(Topic topic: lesson.getTopics()) {
//						if(topic.getId()==tid)
//							return topic;
//					}
//				}
//			}
//		}
//		return null;
		Optional<Topic> op = repository.findById(tid);
		Topic topic = null;
		if (op.isPresent())
			topic = op.get();
		return topic;
	}
	
	@PutMapping("/api/topics/{tid}")
	public Topic updateTopic(HttpSession session, @PathVariable("tid") int tid, 
			@RequestBody Topic topic) {
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;
		
//		Topic top= findTopicById(session,tid);
//		if(top!=null) {
//			top.setWidgets(topic.getWidgets());
//			top.setTitle(topic.getTitle());
//			return top;
//		}
//		return null;
		Topic temp = findTopicById(session, tid);
		temp.setTitle(topic.getTitle());
		return repository.save(temp);
	}

	@DeleteMapping("/api/topics/{tid}")
	public List<Topic> deleteTopic
	(HttpSession session,@PathVariable("tid") int tid) {
		
		currentUser = (Person) session.getAttribute("currentUser");
		if(currentUser==null) return null;

//		List<Course> courses = cs.findAllCourses(session);
//		if(courses==null)
//			return null;
//
//		for(Course course: courses) {
//			for(Module module: course.getModules()) {
//				for(Lesson lesson: module.getLessons()) {
//					for(Topic topic: lesson.getTopics()) {
//						if(topic.getId()==tid) {
//							lesson.getTopics().remove(topic);
//							return lesson.getTopics();
//						}
//					}
//				}
//			}
//		}
//
//		return null;
		repository.deleteById(tid);
		return findAllTopics(session, findTopicById(session, tid).getLesson().getId());
	}



}
