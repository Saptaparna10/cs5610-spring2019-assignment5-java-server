package com.example.cs5610spring2019assignment5serverjava.models;

import java.util.List;

public class Module {
	
	private int id;
	private String title;
	private List<Lesson> lessons;
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
	public List<Lesson> getLessons() {
		return lessons;
	}
	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
	
	
}
