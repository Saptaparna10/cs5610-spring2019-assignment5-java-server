package com.example.cs5610spring2019assignment5serverjava.models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Course {
	
	@Id  
	@GeneratedValue
	(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Person author;
	
	@OneToMany(mappedBy="course",cascade=CascadeType.ALL,orphanRemoval=true, fetch = FetchType.LAZY)
	private List<Module> modules;
	
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
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public Person getAuthor() {
		return author;
	}
	public void setAuthor(Person author) {
		this.author = author;
	}
	
	
}
