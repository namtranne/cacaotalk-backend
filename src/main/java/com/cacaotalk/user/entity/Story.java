package com.cacaotalk.user.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="story")
public class Story extends BaseEntity{
	@Column(nullable=true)
	private String image;
	
	@Column(nullable=true)
	private String video;
	
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@ManyToOne
	private User user;
	
	@ManyToMany
	@JoinTable(
	        name = "story_reactions",
	        joinColumns = @JoinColumn(name = "story_id"),
	        inverseJoinColumns = @JoinColumn(name = "react_user_id")
	    )
	private Set<User> react = new HashSet<>();
	
	public Set<User> getReact() {
		Set<User> returnList = new HashSet<>();
		react.forEach((user)->{
			returnList.add(new User(user.getUserName(), user.getFirstName(), user.getLastName(), user.getImage()));
		});
		return returnList;
	}
	
	@Transient
	public Set<User> realReact(){
		return react;
	}

	public void setReact(Set<User> react) {
		this.react = react;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public User getUser() {
		return new User(user.getUserName(), user.getImage(), user.getId());
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
