package com.cacaotalk.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cacaotalk.user.entity.Comment;
import com.cacaotalk.user.entity.Post;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepo;
	
	public Comment get(int id) {
		return commentRepo.findById(id).get();
	}
	
	public void save(Comment saveComment) {
		commentRepo.save(saveComment);
	}
	
	public void remove(int id) {
		Comment removeComment = commentRepo.findById(id).get();
		commentRepo.delete(removeComment);
	}
}
