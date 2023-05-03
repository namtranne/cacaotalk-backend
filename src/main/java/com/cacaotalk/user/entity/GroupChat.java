package com.cacaotalk.user.entity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name= "group_chat")
public class GroupChat extends BaseEntity{
	@ManyToMany
	Set<User> members = new HashSet<>();
	
	@Column(name="image") 
	private String image;
	
	@Column(name="name")
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<User> getMembers() {
		List<User> res = new LinkedList<>();
		members.forEach((user)->{
			res.add(new User(user.getUserName(), user.getImage(), user.getId()));
		});
		return res;
	}

	public void setMembers(Set<User> members) {
		this.members = members;
	}

	public List<GroupMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<GroupMessage> messages) {
		this.messages = messages;
	}

	public User getAdmin() {
		return new User(admin.getUserName(), admin.getImage(), admin.getId());
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	@OneToMany(mappedBy="group")
	List<GroupMessage> messages = new LinkedList<>();
	
	@OneToOne
	User admin;
	
	public GroupMessage getLastMessage() {
		if(messages.size()==0) return null;
		return messages.get(messages.size()-1);
	}
	
	public void addMember(User user) {
		members.add(user);
	}

	public void removeUser(User member) {
		// TODO Auto-generated method stub
		members.remove(member);
	}
	
}
