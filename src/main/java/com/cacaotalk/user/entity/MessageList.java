package com.cacaotalk.user.entity;

import java.util.List;

public class MessageList {
	private User receiver;
	private List<Message> messages;
	private Message lastMessage;

	public MessageList(User user, List<Message> messages2, Message message) {
		// TODO Auto-generated constructor stub
		receiver= user;
		messages=messages2;
		lastMessage=message;
	}
	
	public MessageList() {
		
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public Message getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(Message lastMessage) {
		this.lastMessage = lastMessage;
	}
	
}
