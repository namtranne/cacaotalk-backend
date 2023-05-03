package com.cacaotalk.user.service;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cacaotalk.user.entity.Comment;
import com.cacaotalk.user.entity.Post;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer>{
	
}
