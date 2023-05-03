package com.cacaotalk.user.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "post")
public class Post extends BaseEntity{
	@Column(name="description", nullable=false)
	private String description;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
	
	@Column(nullable=true)
	private String image;
	
	@Column(nullable=true)
	private String video;
	
	@Column
	private boolean isHidingComment=false;
	
	@Column
	private boolean isHidingReaction=false;
	
	public boolean isHidingComment() {
		return isHidingComment;
	}

	public void setHidingComment(boolean isHidingComment) {
		this.isHidingComment = isHidingComment;
	}

	public boolean isHidingReaction() {
		return isHidingReaction;
	}

	public void setHidingReaction(boolean isHidingReaction) {
		this.isHidingReaction = isHidingReaction;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@ManyToOne
	private User user;

	public Post() {
		
	}
	
	@ManyToMany
	@JoinTable(
	        name = "post_reactions",
	        joinColumns = @JoinColumn(name = "post_id"),
	        inverseJoinColumns = @JoinColumn(name = "react_user_id")
	    )
	private Set<User> react = new HashSet<>();
	
	public Post(String text) {
		this.description=text;
	}

	public User getUser() {
		User userDTO = new User(user.getUserName(), user.getFirstName(), user.getLastName(), user.getImage());
		return userDTO;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

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

}
