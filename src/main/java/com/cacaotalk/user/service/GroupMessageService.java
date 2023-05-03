package com.cacaotalk.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cacaotalk.user.entity.GroupMessage;

@Service
public class GroupMessageService {
	@Autowired GroupMessageRepository repo;
	public GroupMessage get(int id) {
		return repo.findById(id).get();
	}
	
	public void save(GroupMessage groupMessage) {
		repo.save(groupMessage);
	}
}
