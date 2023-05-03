package com.cacaotalk.user.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{
	@Column(name="description", nullable=false)
	private String description;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Post post;
	
	public Comment() {
		
	}
	
	public Comment(String description, User user, Post post) {
		this.description=description;
		this.user= user;
		this.post= post;
		this.time = LocalDateTime.now();
	}
	
	public User getUser() {
		User copyUser =new User(user.getUserName(), user.getImage() ,user.getId());
		return copyUser;
		
	}
	
	public String getDescription() {
		return description;
	}
	
	@ManyToMany
	@JoinTable(
	        name = "comment_reactions",
	        joinColumns = @JoinColumn(name = "comment_id"),
	        inverseJoinColumns = @JoinColumn(name = "react_user_id")
	    )
	private Set<User> react = new HashSet<>();
	
}
