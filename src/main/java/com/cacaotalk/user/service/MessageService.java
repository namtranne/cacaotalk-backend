package com.cacaotalk.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cacaotalk.user.entity.Message;

@Service
public class MessageService {
	@Autowired MessageRepository repo;
	
	public List<Message> getMessagesOfTwoUser(int user1, int user2) {
		return repo.getMessagesOfTwoUser(user1, user2);
	}
	
	public void save(Message message) {
		repo.save(message);
	}
	
	public void seenMessages(int user1, int user2) {
		repo.seenMessages(user1, user2);
	}
	
	public Message get(int id) {
		return repo.findById(id).get();
	}
	
}
