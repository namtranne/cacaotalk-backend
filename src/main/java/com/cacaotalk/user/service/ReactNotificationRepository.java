package com.cacaotalk.user.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cacaotalk.user.entity.FollowNotification;
import com.cacaotalk.user.entity.ReactNotification;

public interface ReactNotificationRepository extends PagingAndSortingRepository<ReactNotification, Integer> {
	@Query("SELECT u FROM ReactNotification u WHERE u.receiver.userName=:userName")
	public List<ReactNotification> getUserReactNotification(@Param("userName") String userName); // ten phai giong cai param
}
