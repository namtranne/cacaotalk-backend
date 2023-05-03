package com.cacaotalk.user.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cacaotalk.user.entity.Comment;
import com.cacaotalk.user.entity.CommentNotification;
import com.cacaotalk.user.entity.GroupChat;
import com.cacaotalk.user.entity.Message;
import com.cacaotalk.user.entity.Post;
import com.cacaotalk.user.entity.ReactNotification;
import com.cacaotalk.user.entity.User;
import com.cacaotalk.user.service.CommentService;
import com.cacaotalk.user.service.CommonService;
import com.cacaotalk.user.service.MessageService;
import com.cacaotalk.user.service.NotificationService;
import com.cacaotalk.user.service.PostService;
import com.cacaotalk.user.service.UserService;


@CrossOrigin
@RestController
public class PostController {
	
	@Autowired
	 private PostService service;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private NotificationService notiService;
	
	@Autowired
	private MessageService messageService;
	
	@GetMapping("/post/get/{id}")
	public Post test(@PathVariable(name = "id") Integer id) {
		return service.get(id);
	}
	
	@PostMapping("/post/save")
	public void savePost(String text, Integer userId, MultipartFile image, MultipartFile video) {
		Post savePost = new Post(text);
		User user=userService.get(userId);
		savePost.setUser(user);
		savePost.setTime(LocalDateTime.now());
		try {
			if(image!=null) {
				String uploadDir =commonService.uploadImage(image, image.getName());
				savePost.setImage(uploadDir);
			} else if (video!=null) {
				String uploadDir =commonService.uploadImage(video, video.getName());
				savePost.setVideo(uploadDir);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service.save(savePost);
	}
	
	@PostMapping("/post/edit/{id}")
	public void editPost(String text, @PathVariable(name = "id") Integer id) {
		Post post = service.get(id);
		post.setDescription(text);
		service.save(post);
		
	}
	
	@GetMapping("/post/delete/post/{id}")
	public void deletePost(@PathVariable(name = "id") Integer id) {
		Post post =service.get(id);
		String imageUrl = post.getImage();
		String videoUrl = post.getVideo();
		if(imageUrl!=null) {
			String folder = imageUrl
					.replace("https://storage.googleapis.com/download/storage/v1/b/cacaotalk-36115.appspot.com/o/", "")
					.replaceAll("%2F", "/")
					.replaceAll("[^/]+/$", "");
			commonService.deleteFolderAndContents(folder.substring(folder.indexOf("images/"), folder.lastIndexOf("/")));
		} else if(videoUrl!=null) {
			String folder = videoUrl
					.replace("https://storage.googleapis.com/download/storage/v1/b/cacaotalk-36115.appspot.com/o/", "")
					.replaceAll("%2F", "/")
					.replaceAll("[^/]+/$", "");
			commonService.deleteFolderAndContents(folder.substring(folder.indexOf("images/"), folder.lastIndexOf("/")));
		}
		service.remove(id);
		
	}
	
	@PostMapping("/post/upload/comment")
	public void uploadComment(Integer postId, String comment, String userName) {
		Post post = service.get(postId);
//		post.getComments().add()
		User user = userService.getUserByUserName(userName);
		User receiver = userService.getUserByUserName(post.getUser().getUserName());
		Comment newComment = new Comment(comment, user, post);
		commentService.save(newComment);
		if(user.getId()==receiver.getId()) {
			return;
		}
		CommentNotification noti = new CommentNotification();
		noti.setSender(user);
		noti.setReceiver(receiver);
		notiService.saveCommentNoti(noti);
	}
	
	@PostMapping("/post/like")
	public void likePost(Integer postId, String userName) {
		Post post = service.get(postId);
//		post.getComments().add()
		User sender = userService.getUserByUserName(userName);
		User receiver = userService.getUserByUserName(post.getUser().getUserName());
		post.realReact().add(sender);
		service.save(post);
//		if(sender.getId()==receiver.getId()) {
//			return;
//		}
		ReactNotification noti = new ReactNotification();
		noti.setReceiver(receiver);
		noti.setSender(sender);
		noti.setPost(post);
		notiService.saveReactNoti(noti);
	}
	
	@PostMapping("/post/unlike")
	public void unlikePost(Integer postId, String userName) {
		Post post = service.get(postId);
//		post.getComments().add()
		User user = userService.getUserByUserName(userName);
		post.realReact().remove(user);
		service.save(post);
	}
	
	@GetMapping("/post/comment/get/{postId}")
	public List<Comment> getPostComment(@PathVariable(name="postId") Integer id) {
		return service.get(id).getComments();
	}
	
	@GetMapping("/post/hide/comments/{postId}")
	public void hideComments(@PathVariable(name="postId") Integer id) {
		Post pos = service.get(id);
		pos.setHidingComment(!pos.isHidingComment());
		service.save(pos);
	}
	
	@GetMapping("/post/hide/reactions/{postId}")
	public void hideReactions(@PathVariable(name="postId") Integer id) {
		Post pos = service.get(id);
		pos.setHidingReaction(!pos.isHidingReaction());
		service.save(pos);
	}
	
	@PostMapping("/post/share/{port}/{postId}/{userName}")
	public void sharePost(@RequestBody List<String> userList, @PathVariable(name="postId") Integer postId, @PathVariable(name="userName") String userName, @PathVariable(name="port") String port) {
		String message = "localhost:" +port  +"/post/" +postId;
		User sender = userService.getUserByUserName(userName);
		userList.forEach((user)->{
			messageService.save(new Message(message, sender, userService.getUserByUserName(user)));
		});
		
	}
}
