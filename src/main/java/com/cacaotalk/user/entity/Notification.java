package com.cacaotalk.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@MappedSuperclass
public class Notification extends BaseEntity{
	@ManyToOne
	protected User sender;
	
	@ManyToOne
	protected User receiver;
	
	@Column
	protected String description;
	
	@Column(nullable=false)
	protected boolean isRead=false; 
	

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
 
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getSender() {
		return new User(sender);
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return new User(receiver);
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	 
	
	
}
