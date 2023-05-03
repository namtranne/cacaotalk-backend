package com.cacaotalk.user.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cacaotalk.user.entity.GroupChat;
import com.cacaotalk.user.entity.GroupMessage;
import com.cacaotalk.user.entity.Message;
import com.cacaotalk.user.entity.Post;
import com.cacaotalk.user.entity.User;
import com.cacaotalk.user.service.CommonService;
import com.cacaotalk.user.service.GroupChatService;
import com.cacaotalk.user.service.GroupMessageService;
import com.cacaotalk.user.service.UserService;

@CrossOrigin
@RestController
public class GroupController {
	
	@Autowired GroupChatService service;
	@Autowired UserService userService;
	@Autowired GroupMessageService groupMessageService;
	@Autowired CommonService commonService;
	
	@GetMapping("/group/get/{id}")
	public List<GroupChat> getGroup(@PathVariable(name="id") Integer id) {
		return service.getUserGroups(id);
	}
	
	@PostMapping("/group/message/save")
	public void saveGroupMessage(MultipartFile image, MultipartFile video, String message, Integer sender_id, Integer group_id) {
		User sender = userService.get(sender_id);
		GroupMessage groupMessage= new GroupMessage(message, sender);
		GroupChat group = service.getGroup(group_id);
		groupMessage.setGroup(group);
		try {
			if(image!=null) {
				String uploadDir =commonService.uploadImage(image, image.getName());
				groupMessage.setImage(uploadDir);
			} else if (video!=null) { 
				String uploadDir =commonService.uploadImage(video, video.getName());
				groupMessage.setVideo(uploadDir);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		groupMessageService.save(groupMessage);
		
	}
	
	@GetMapping("/group/message/like/{userId}/{messageId}")
	public void likeMessage(@PathVariable(name="userId")  Integer id1, @PathVariable(name="messageId") int id2) {
		User user = userService.get(id1);
		GroupMessage message =groupMessageService.get(id2);
		if(message.realReact().contains(user)) {
			message.realReact().remove(user);
		}
		else {
			message.realReact().add(user);
		}
		groupMessageService.save(message);
		
	}
	
	@GetMapping("/group/search/{userId}/{keyword}")
	public List<GroupChat> searchGroup(@PathVariable(name="keyword") String keyword, @PathVariable(name="userId") Integer id) { 
		List<GroupChat> res= new LinkedList<>();
		service.getUserGroups(id).forEach((group)->{
			if(group.getName().contains(keyword)) {
				res.add(group);
			}
		});
		return res;
	}
	
	@GetMapping("/group/get/follower/{userName}")
	public Set<User> getFollowers(@PathVariable(name="userName") String userName) {
		Set<User> res = new HashSet<>(); 
		userService.getUserByUserName(userName).getFollowers().forEach((follower)->{
			res.add(new User(follower.getUserName(), follower.getImage(), follower.getId()));
		});
		return res;
	}
	
	@PostMapping("/group/create")
	public void createGroup(@RequestBody List<String> userList) {
		GroupChat group = new GroupChat();
		userList.forEach((userName)->{
			User user =userService.getUserByUserName(userName);
			group.addMember(user);
			if(group.getName()==null || group.getName().length() < 10) {	
				group.setName(group.getName()==null ? user.getLastName() : group.getName() +"_" + user.getLastName());
			}
		});
		group.setAdmin(userService.getUserByUserName(userList.get(userList.size()-1)));
		group.setImage("https://www.jacana.co.uk/wp-content/uploads/2015/04/user-groups.png");
		group.setTime(LocalDateTime.now());
		service.save(group);
	}
	
	@GetMapping("/group/message/seen/{userId}/{groupId}")
	public void seenGroupMessages(@PathVariable(name="userId")  Integer id1, @PathVariable(name="groupId") Integer id2) {
		GroupChat group= service.getGroup(id2);
		GroupMessage lastMessage = group.getLastMessage();
		if(lastMessage!=null) {
			int message_id = lastMessage.getId();
			GroupMessage groupMessage = groupMessageService.get(message_id);
			groupMessage.addReadBy(userService.get(id1));
			groupMessageService.save(groupMessage);
		}
		
	}
	
	@PostMapping("/group/set/name")
	public void setGroupName(int groupId, String groupName) {
		GroupChat group = service.getGroup(groupId);
		group.setName(groupName);
		service.save(group);
	}
	
	@PostMapping("/post/set/image")
	public void savePost(MultipartFile image, int groupId) {
		GroupChat group = service.getGroup(groupId);
		String folder = group.getImage()
				.replace("https://storage.googleapis.com/download/storage/v1/b/cacaotalk-36115.appspot.com/o/", "")
				.replaceAll("%2F", "/")
				.replaceAll("[^/]+/$", "");
		commonService.deleteFolderAndContents(folder);
		String uploadDir;
		try {
			uploadDir = commonService.uploadImage(image, image.getName());
			group.setImage(uploadDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service.save(group);
	}
	
	@GetMapping("/group/member/remove/{groupId}/{memberId}")
	public void removeGroupMember(@PathVariable(name="groupId") Integer id, @PathVariable(name="memberId") Integer memberId) {
		User member = userService.get(memberId);
		GroupChat group = service.getGroup(id);
		group.removeUser(member);
		service.save(group);
	}
	
	@GetMapping("/group/set/admin/{groupId}/{memberId}")
	public void setGroupAdmin(@PathVariable(name="groupId") Integer id, @PathVariable(name="memberId") Integer memberId) {
		User admin = userService.get(memberId);
		GroupChat group = service.getGroup(id);
		group.setAdmin(admin);
		service.save(group);
	}
	
	@GetMapping("/group/leave/{groupId}/{memberId}")
	public void leaveGroup(@PathVariable(name="groupId") Integer id, @PathVariable(name="memberId") Integer memberId) {
		User member = userService.get(memberId);
		GroupChat group = service.getGroup(id);
		group.removeUser(member);
		service.save(group);
	}
	
	@PostMapping("/group/manage/{groupId}")
	public void manageGroup(@PathVariable(name="groupId") Integer id, @RequestBody List<String> userList) {
		GroupChat group = service.getGroup(id);
		Set<User> users = new  HashSet<>();
		userList.forEach((userName)->{
			users.add(userService.getUserByUserName(userName));
		});
		group.setMembers(users);
		service.save(group);
	}
	
}
