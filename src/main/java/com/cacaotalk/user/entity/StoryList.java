package com.cacaotalk.user.entity;

import java.util.List;

public class StoryList {
	private User user;
	public List<Story> stories;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Story> getStories() {
		return stories;
	}
	public void setStories(List<Story> stories) {
		this.stories = stories;
	}
	public StoryList(User user, List<Story> stories) {
		this.user = user;
		this.stories = stories;
	}
	
}
