package com.cacaotalk.user.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cacaotalk.user.entity.HighlightStory;
import com.cacaotalk.user.entity.Message;
import com.cacaotalk.user.entity.Post;
import com.cacaotalk.user.entity.User;

public interface HighlightRepository extends PagingAndSortingRepository<HighlightStory, Integer>{
	@Query("SELECT u FROM HighlightStory u WHERE u.user.userName=:userName")
	public List<HighlightStory> getUserHighlightStories(@Param("userName") String userName); // ten phai giong cai param
}