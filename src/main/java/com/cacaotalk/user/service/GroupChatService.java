package com.cacaotalk.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cacaotalk.user.entity.GroupChat;

@Service
public class GroupChatService {
	@Autowired GroupChatRepository repo;
	public GroupChat getGroup(int id) {
		return repo.findById(id).get();
	}
	
	public List<GroupChat> getUserGroups(int id) {
		return repo.findByMembers_Id(id);
	}
	
	public void save(GroupChat group) {
		repo.save(group);
	}
			
}
