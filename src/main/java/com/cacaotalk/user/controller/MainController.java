package com.cacaotalk.user.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cacaotalk.user.entity.CommentNotification;
import com.cacaotalk.user.entity.FollowNotification;
import com.cacaotalk.user.entity.Message;
import com.cacaotalk.user.entity.Notification;
import com.cacaotalk.user.entity.ReactNotification;
import com.cacaotalk.user.entity.Response;
import com.cacaotalk.user.entity.User;
import com.cacaotalk.user.security.CacaoUserDetails;
import com.cacaotalk.user.service.CommonService;
import com.cacaotalk.user.service.NotificationService;
import com.cacaotalk.user.service.UserService;



@CrossOrigin
@RestController
public class MainController {
	
	@Autowired
	 private UserService service;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private NotificationService notiService;
	
	@GetMapping("/user/get/{username}")
	public User test(@PathVariable String username) {
		User user = service.getUserByUserName(username);
		User copyUser =  new User(user);
		return copyUser;
	}
	
	@GetMapping("/user/get/suggested/{username}")
	public List<User> getSuggestedUsers(@PathVariable String username) {
		List<User> returnList = new LinkedList<>();
		service.listAll().forEach((user)->{
			returnList.add(new User(user.getUserName(), user.getImage(), user.getId()));
		});
		return returnList;
	}
	
	@GetMapping("/user/follow/{username}")
	public void followUser(@PathVariable String username, @AuthenticationPrincipal CacaoUserDetails userLogged) {
		User user = service.getUserByUserName(userLogged.getUsername());
		User followUser = service.getUserByUserName(username);
		followUser.getFollowers().add(user);
		service.save(followUser);
		FollowNotification noti = new FollowNotification();
		noti.setSender(user);
		noti.setReceiver(followUser);
		notiService.saveFollowNoti(noti);
	}
	
	@GetMapping("/user/unfollow/{username}")
	public void unFollowUser(@PathVariable String username) {
		User user = service.getUserByUserName("namtranne");
		User followUser = service.getUserByUserName(username);
		followUser.getFollowers().remove(user);
		service.save(followUser);
	}
	
	@PostMapping("/user/edit/password")
	public Response changePassword(String userName, String currentPassword, String newPassword) {
		User user = service.getUserByUserName(userName);
		if(passwordEncoder.matches(currentPassword, user.getPassword())) {
			user.setPassword(passwordEncoder.encode(newPassword));
			service.save(user);
			return new Response("Password changed successfuly", true);
		}
		service.save(user);
		return new Response("Current password incorrect", false);
	}
	
	@PostMapping("/user/new")
	public Response createUser(@RequestBody User user) {
		User checkUser = service.getUserByUserName(user.getUserName());
		if(checkUser!=null) {
			return new Response("Your username is already taken!", false);
		}
		user.setImage("https://i.stack.imgur.com/l60Hf.png");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		service.save(user);
		return new Response("Sign up success!", true);
	}
	
	@GetMapping("/user/search/{keyword}")
	public List<User> searchUser(@PathVariable(name="keyword") String keyword) { 
		List<User> res = new LinkedList<>();
		service.search(keyword).forEach((user)->{
			res.add(new User(user.getUserName(), user.getImage(), user.getId()));
		});
		return res;
	}
	
	@PostMapping("/user/set/image")
	public void setUserImage(MultipartFile image, Integer userId) {
		User user = service.get(userId);
		String imageUrl = user.getImage();
		if(imageUrl!=null && imageUrl.contains("https://storage.googleapis.com/download/storage")) {
			String folder = imageUrl
					.replace("https://storage.googleapis.com/download/storage/v1/b/cacaotalk-36115.appspot.com/o/", "")
					.replaceAll("%2F", "/")
					.replaceAll("[^/]+/$", "");
			commonService.deleteFolderAndContents(folder.substring(folder.indexOf("images/"), folder.lastIndexOf("/")));
		}
		try {
			if(image!=null) {
				String uploadDir =commonService.uploadImage(image, image.getName());
				user.setImage(uploadDir);
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service.save(user);
	}
	
	@PostMapping("/user/edit/account")
	public void editUser(String userName, String firstName, String lastName, String description, String email) {
		User user = service.getUserByUserName(userName);
		user.setDescription(description);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		service.save(user);
	}
	
	@GetMapping("/user/notification/get/{userName}")
	public List<Notification> getUserNotification(@PathVariable(name="userName") String userName) {
		return notiService.getUserNotification(userName);
	}
	
	@GetMapping("/user/notification/react/read/{id}")
	public void reactNotificationRead(@PathVariable(name="id") Integer id) {
		ReactNotification noti =notiService.getReactNoti(id);
		noti.setRead(true);
		notiService.saveReactNoti(noti);
	}
	
	@GetMapping("/user/notification/follow/read/{id}")
	public void followNotificationRead(@PathVariable(name="id") Integer id) {
		FollowNotification noti =notiService.getFollowNoti(id);
		noti.setRead(true);
		notiService.saveFollowNoti(noti);
	}
	
	@GetMapping("/user/notification/comment/read/{id}")
	public void commentNotificationRead(@PathVariable(name="id") Integer id) {
		CommentNotification noti =notiService.getCommentNoti(id);
		noti.setRead(true);
		notiService.saveCommentNoti(noti);
	}
}
