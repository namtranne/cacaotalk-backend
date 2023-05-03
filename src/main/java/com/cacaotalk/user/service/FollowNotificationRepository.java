package com.cacaotalk.user.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cacaotalk.user.entity.FollowNotification;
import com.cacaotalk.user.entity.HighlightStory;

public interface FollowNotificationRepository extends PagingAndSortingRepository<FollowNotification, Integer> {
	@Query("SELECT u FROM FollowNotification u WHERE u.receiver.userName=:userName")
	public List<FollowNotification> getUserFollowNotification(@Param("userName") String userName); // ten phai giong cai param
}
