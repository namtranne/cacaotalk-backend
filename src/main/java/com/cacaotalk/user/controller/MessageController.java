package com.cacaotalk.user.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cacaotalk.user.entity.Message;
import com.cacaotalk.user.entity.MessageList;
import com.cacaotalk.user.entity.User;
import com.cacaotalk.user.service.CommonService;
import com.cacaotalk.user.service.GroupChatService;
import com.cacaotalk.user.service.MessageService;
import com.cacaotalk.user.service.UserService;

@RestController
@CrossOrigin
public class MessageController {
	@Autowired MessageService service;
	@Autowired UserService userService;
	@Autowired CommonService commonService;
	@Autowired GroupChatService groupService;
	
	@GetMapping("/message/get/{id1}/{id2}")
	public List<Message> getMessageOfTwoUsers(@PathVariable(name="id1") Integer id1, @PathVariable(name="id2") Integer id2) {
		return service.getMessagesOfTwoUser(id1, id2);
	}
	
	@GetMapping("/message/list/{id}")
	public List<MessageList> getMessageList(@PathVariable(name="id") Integer id) {
		List<MessageList> res= new LinkedList<>();
		userService.getMessageList(id).forEach((user)->{
			List<Message> messages = getMessageOfTwoUsers(id, user.getId());
			res.add(new MessageList(new User(user.getUserName(), user.getFirstName(), user.getLastName(), user.getImage(), user.getId()), messages, messages.size() > 0 ? messages.get(messages.size()-1) : null));
		});
		return res;
	}
	@PostMapping("/message/save")
	public void saveMessage(MultipartFile image, MultipartFile video, String message, Integer sender_id, Integer receiver_id) {
		User sender = userService.get(sender_id);
		User receiver = userService.get(receiver_id);
		Message saveMessage = new Message(message, sender, receiver);
		try {
			if(image!=null) {
				String uploadDir =commonService.uploadImage(image, image.getName());
				saveMessage.setImage(uploadDir);
			} else if (video!=null) { 
				String uploadDir =commonService.uploadImage(video, video.getName());
				saveMessage.setVideo(uploadDir);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service.save(saveMessage);
		
	}
	
	@GetMapping("/message/seen/{userId}/{chatUserId}")
	public void seenMessages(@PathVariable(name="userId")  Integer id1, @PathVariable(name="chatUserId") Integer id2) {
		service.seenMessages(id1,id2);
	}
	
	@GetMapping("/message/like/{userId}/{messageId}")
	public void likeMessage(@PathVariable(name="userId")  Integer id1, @PathVariable(name="messageId") Integer id2) {
		User user = userService.get(id1);
		Message message =service.get(id2);
		if(message.realReact().contains(user)) {
			message.realReact().remove(user);
		}
		else {
			message.realReact().add(user);
		}
		service.save(message);
		
	}
}
