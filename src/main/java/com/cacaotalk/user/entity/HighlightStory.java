package com.cacaotalk.user.entity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Table(name = "highlight_story")
public class HighlightStory extends BaseEntity {
	@ManyToOne
	User user;
	
	@Column(name="name")
	String name; 
	
	@Column(name="image")
	String image;
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(
	        name = "highlight_stories",
	        joinColumns = @JoinColumn(name = "highlight_story_id"),
	        inverseJoinColumns = @JoinColumn(name = "story_id")
	    )
	List<Story> stories = new LinkedList<>();

	public User getUser() {
		return new User(user);
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Story> getStories() {
		return stories;
	}

	public void setStories(List<Story> stories) {
		this.stories = stories;
	}
	
	
}
