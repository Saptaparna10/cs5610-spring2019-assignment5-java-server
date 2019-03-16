package com.example.cs5610spring2019assignment5serverjava.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Widget {

	@Id  
	@GeneratedValue
	(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String title;
	private String type;
	private String text;
	private int size;
	private int orderOfWidget;
	/**--heading--**/
	//private String headingSize;
	/**--Image--**/
	private String src;
	/**--para--**/
	//private String text;
	/**list--*/
	private String listItems;
	private String listType;
	
	
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getListItems() {
		return listItems;
	}
	public void setListItems(String listItems) {
		this.listItems = listItems;
	}
	public boolean isOrdered() {
		return ordered;
	}
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}
	private boolean ordered;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Topic topic;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getOrderOfWidget() {
		return orderOfWidget;
	}
	public void setOrderOfWidget(int orderOfWidget) {
		this.orderOfWidget = orderOfWidget;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public String isListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	
}
