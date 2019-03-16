package com.example.cs5610spring2019assignment5serverjava.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.example.cs5610spring2019assignment5serverjava.models.*;
import com.example.cs5610spring2019assignment5serverjava.repositories.TopicRepository;
import com.example.cs5610spring2019assignment5serverjava.repositories.WidgetRepository;


@RestController
@CrossOrigin(origins = "https://das-saptaparna-assignment5.herokuapp.com", allowCredentials="true")
public class WidgetService {

	@Autowired
	TopicRepository  topicRepository;
	
	@Autowired
	WidgetRepository widgetRepository;

	@PostMapping("/api/topics/{topicId}/widgets")
	public Iterable<Widget> createWidget(@PathVariable("topicId") int topicId, @RequestBody Widget widget/*@RequestBody List<Widget> widgets*/) {


		Optional<Topic>  data4 = topicRepository.findById(topicId);
		
		
		if (data4.isPresent()) {
			Topic topic = data4.get();
			widget.setTopic(topic);
			
			widgetRepository.save(widget);
			return findAllWidgetsForTopic(topicId);
		}
		
		return null;

	}

	@GetMapping("/api/widget")
	public Iterable<Widget> findAllWidgets() {
		return widgetRepository.findAll();
	}
	
	
	@GetMapping("/api/topics/{topicId}/widgets")
	public Iterable<Widget> findAllWidgetsForTopic(@PathVariable("topicId") int topicId){

		Optional<Topic>  data4 = topicRepository.findById(topicId);

		if (data4.isPresent()) {
			
			Topic topic=data4.get();

			return topic.getWidgets();
		}

		return null;
	}
		

	@DeleteMapping("/api/topics/{topicId}/widgets/{widgetId}")
	public Iterable<Widget> deleteWidget(@PathVariable("topicId") int tid,
							@PathVariable("widgetId") int id) {
		
		widgetRepository.deleteById(id);
		return findAllWidgetsForTopic(tid);
	}
	
	
	@GetMapping("/api/widgets/{widgetId}")
	public Widget findWidgetById(@PathVariable("widgetId") int id) {
		Optional<Widget> op = widgetRepository.findById(id);
		Widget widget = null;
		if (op.isPresent())
			widget = op.get();
		return widget;
	}
	

	@PutMapping("/api/widgets/{widgetId}")
	public Iterable<Widget>  updateWidget(@PathVariable("widgetId") int id, @RequestBody Widget widget) {
		Widget temp = findWidgetById(id);
		temp.setText(widget.getText());
		widgetRepository.save(temp);
		return findAllWidgetsForTopic(temp.getTopic().getId());
	}

	@PostMapping("/api/save/topics/{topicId}/widgets")
	public Iterable<Widget> save(@PathVariable("topicId") int topicId, @RequestBody List<Widget> widgets){
		Optional<Topic>  data = topicRepository.findById(topicId);
		if (data.isPresent()) {
			Topic topic = data.get();
			List<Widget> ws= topic.getWidgets();
			
			for(Widget wb: ws) {
				for(Widget wc: widgets) {
					if(wb.getId()==wc.getId()) {
						break;
					}
				}
				deleteWidget(topic.getId(), wb.getId());
			}
			
			for(Widget widget: widgets) {
				widget.setTopic(topic);
				widgetRepository.save(widget);
			}
			//widget.setTopic(topic);
			
			//widgetRepository.save(widget);
			return findAllWidgetsForTopic(topicId);
		}
		return null;
	}
	
}
