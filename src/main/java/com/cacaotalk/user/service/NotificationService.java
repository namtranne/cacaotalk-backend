package com.cacaotalk.user.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cacaotalk.user.entity.CommentNotification;
import com.cacaotalk.user.entity.FollowNotification;
import com.cacaotalk.user.entity.Notification;
import com.cacaotalk.user.entity.ReactNotification;
import com.cacaotalk.user.entity.User;

@Service
public class NotificationService {
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private FollowNotificationRepository followNotiRepo;
	
	@Autowired
	private CommentNotificationRepository commentNotiRepo;
	
	@Autowired
	private ReactNotificationRepository reactNotiRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public List<Notification> getUserNotification(String userName) {
		List<Notification> res= new LinkedList<>();
		res.addAll(commentNotiRepo.getUserCommentNotification(userName));
		res.addAll(reactNotiRepo.getUserReactNotification(userName));
		res.addAll(followNotiRepo.getUserFollowNotification(userName));
		res.sort((one,two) -> two.getTime().compareTo(one.getTime()));
		return res;
	}
	
	public void saveCommentNoti(CommentNotification res) {
		commentNotiRepo.save(res);
	}
	
	public void saveFollowNoti(FollowNotification res) {
		followNotiRepo.save(res);
	}
	public void saveReactNoti(ReactNotification res) {
		reactNotiRepo.save(res);
	}
	
	public FollowNotification getFollowNoti(int id) {
		return followNotiRepo.findById(id).get();
	}
	
	public CommentNotification getCommentNoti(int id) {
		return commentNotiRepo.findById(id).get();
	}
	
	public ReactNotification getReactNoti(int id) {
		return reactNotiRepo.findById(id).get();
	}
}
