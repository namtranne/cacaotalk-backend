package com.cacaotalk.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cacaotalk.user.entity.GroupMessage;
import com.cacaotalk.user.entity.HighlightStory;

@Service
public class HighlightService {
	@Autowired HighlightRepository repo;
	public HighlightStory get(int id) {
		return repo.findById(id).get();
	}
	
	public List<HighlightStory> getUserHighlightStories(String userName) {
		List<HighlightStory> res = repo.getUserHighlightStories(userName);
		res.sort((first,second) -> first.getTime().compareTo(second.getTime()));
		return res;
	}
	
	public void save(HighlightStory highlightStory) {
		repo.save(highlightStory);
	}
}
