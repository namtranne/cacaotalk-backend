package com.cacaotalk.user.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;

@Entity
@Table(name="group_messages") 
public class GroupMessage extends BaseEntity{
    public void setGroup(GroupChat group) {
		this.group = group;
	}

	@ManyToOne
    private User sender;
    
    @ManyToOne
	private GroupChat group;
    
    @Column(name="message")
    private String message;
    
    @Column(name="video")
	private String video;
    
    @ManyToMany
	@JoinTable(
	        name = "group_message_reaction",
	        joinColumns = @JoinColumn(name = "message_id"),
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

	public void setReact(Set<User> react) {
		this.react = react;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name="image")
	private String image;
    
    @ManyToMany
    @JoinTable(name = "message_readers",
        joinColumns = @JoinColumn(name = "message_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> readBy = new HashSet<>();
    
    
    @Column(name="isRead", nullable=false)
    private boolean isRead;

    public GroupMessage() {
    	
    }
    
    public GroupMessage(String Message, User sender) {
    	this.message=Message;
    	this.sender=sender;
    	this.time = LocalDateTime.now();
    }
    
	public User getSender() {
		return new User(sender.getUserName(), sender.getImage(), sender.getId());
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public List<String> getReadBy() {
		List<String> res = new LinkedList<>();
		readBy.forEach((user)->{
			res.add(user.getUserName());
		});
		return res;
	}

	public void setReadBy(Set<User> readBy) {
		this.readBy = readBy;
	}  

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	
	@Transient
	public Set<User> realReact(){
		return react;
	}
	
    public void addReadBy(User user) {
    	readBy.add(user);
    }
    // Constructor, getters, and setters omitted for brevity
}
