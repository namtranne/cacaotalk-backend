package com.cacaotalk.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cacaotalk.user.entity.Post;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepo;
	
	public Post get(int id) {
		return postRepo.findById(id).get();
	}
	
	public void save(Post savePost) {
		postRepo.save(savePost);
	}
	
	public void remove(int id) {
		Post removePost = postRepo.findById(id).get();
		postRepo.delete(removePost);
	}
}
