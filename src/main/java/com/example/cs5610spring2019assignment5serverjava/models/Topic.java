package com.example.cs5610spring2019assignment5serverjava.models;

import java.util.List;

public class Topic {

	private int id;
	private String title;
	private List<Widget> widgets;
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
	public List<Widget> getWidgets() {
		return widgets;
	}
	public void setWidgets(List<Widget> widgets) {
		this.widgets = widgets;
	}
	
}
