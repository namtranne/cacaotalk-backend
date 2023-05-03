package com.cacaotalk.user.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cacaotalk.user.entity.Message;
import com.cacaotalk.user.entity.Post;
import com.cacaotalk.user.entity.Story;
import com.cacaotalk.user.entity.User;

public interface StoryRepository extends PagingAndSortingRepository<Story, Integer>{
	
	@Query("SELECT s FROM Story s WHERE s.user.userName = :userName")
	public List<Story> getStories(@Param("userName")String userName);
}
