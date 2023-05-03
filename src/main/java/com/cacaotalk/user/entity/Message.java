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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="messages")
public class Message extends BaseEntity{
	@ManyToOne
	private User sender;
	
	@ManyToOne
	private User receiver;
	
	@Column(name="message", nullable=true)
	private String message;
	
	@Column(name="isRead", nullable=false)
	private boolean isRead;
	
	@Column(name="isImage", nullable = false)
	private boolean isImage;
	
	@Column(name="image")
	private String image;
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@Column(name="video")
	private String video;

	public boolean isImage() {
		return isImage;
	}

	public void setImage(boolean isImage) {
		this.isImage = isImage;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Message(String message2, User sender2, User receiver2) {
		this.message = message2;
		this.sender=sender2;
		this.receiver=receiver2;
		this.time = LocalDateTime.now();
	}
	
	public Message() {
		
	}

	public String getMessage() {
		return message;
	}
	
	public User getSender() {
		return new User(sender.getUserName());
	}
	
	public User getReceiver() {
		return new User(receiver.getUserName());
	}
	
	@ManyToMany
	@JoinTable(
	        name = "message_reactions",
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
	
	@Transient
	public Set<User> realReact(){
		return react;
	}

	public void setReact(Set<User> react) {
		this.react = react;
	}
}
