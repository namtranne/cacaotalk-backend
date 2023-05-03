package com.cacaotalk.user.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cacaotalk.user.entity.Comment;
import com.cacaotalk.user.entity.CommentNotification;
import com.cacaotalk.user.entity.ReactNotification;

public interface CommentNotificationRepository extends PagingAndSortingRepository<CommentNotification, Integer> {
	@Query("SELECT u FROM CommentNotification u WHERE u.receiver.userName=:userName")
	public List<CommentNotification> getUserCommentNotification(@Param("userName") String userName); // ten phai giong cai param
}
