package com.cacaotalk.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cacaotalk.user.entity.Post;
import com.cacaotalk.user.entity.Story;

@Service
public class StoryService {
	@Autowired
	private StoryRepository storyRepo;
	
	public Story get(int id) {
		return storyRepo.findById(id).get();
	}
	
	public void save(Story saveStory) {
		storyRepo.save(saveStory);
	}
	
	public void remove(int id) {
		Story removeStory = storyRepo.findById(id).get();
		storyRepo.delete(removeStory);
	}
	
	public List<Story> getStories(String userName) {
		return storyRepo.getStories(userName);
	}
}
