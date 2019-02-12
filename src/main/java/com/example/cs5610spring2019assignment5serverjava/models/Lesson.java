package com.example.cs5610spring2019assignment5serverjava.models;

import java.util.List;

public class Lesson {
	
	private int id;
	private String title;
	private List<Topic> topics;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Topic> getTopics() {
		return topics;
	}
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	
	
}
