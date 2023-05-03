package com.cacaotalk.user.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cacaotalk.user.entity.HighlightStory;
import com.cacaotalk.user.entity.Post;
import com.cacaotalk.user.entity.Story;
import com.cacaotalk.user.entity.StoryList;
import com.cacaotalk.user.entity.User;
import com.cacaotalk.user.service.CommonService;
import com.cacaotalk.user.service.HighlightService;
import com.cacaotalk.user.service.StoryService;
import com.cacaotalk.user.service.UserService;
//import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class StoryController {
	@Autowired StoryService service;
	@Autowired UserService userService;
	@Autowired HighlightService highlightService;
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/story/get/following/{userName}")
	public List<StoryList> getStory(@PathVariable(name="userName") String userName) {
		List<StoryList> res= new LinkedList<>();
		User user=userService.getUserByUserName(userName);
		if(service.getStories(userName).size()>0) {
			res.add(new StoryList(new User(user.getUserName(), user.getImage(), user.getId()), service.getStories(userName)));
		}
			
		user.getFollowing().forEach((loopUser)->{
			List<Story>  list= service.getStories(loopUser.getUserName());
			if(list.size()>0) {
				res.add(new StoryList(new User(loopUser.getUserName(), loopUser.getImage(), loopUser.getId()), service.getStories(loopUser.getUserName())));
			}
		});
		return res;
		
	}
	
	@GetMapping("/story/get/{userName}")
	public List<StoryList> getUserStory(@PathVariable(name="userName") String userName) {
		List<StoryList> res= new LinkedList<>();
		User user=userService.getUserByUserName(userName);
		if(service.getStories(userName).size()>0) {
			res.add(new StoryList(new User(user.getUserName(), user.getImage(), user.getId()), service.getStories(userName)));
		}
		return res;
		
	}
	
	@PostMapping("/story/highlight/create")
	public void createHighlight(MultipartFile image,
	                             @RequestParam("userName") String userName,
	                             @RequestParam("name") String name,
	                             @RequestParam("storyList") List<Integer> storyList) {
	    List<Story> highlightList = new LinkedList<>();
	    storyList.forEach((storyId) ->{
	    	highlightList.add(service.get(storyId));
	    });
	    highlightList.sort((one,two) -> one.getTime().compareTo(two.getTime()));
	    HighlightStory highlightStory = new HighlightStory();
	    highlightStory.setUser(userService.getUserByUserName(userName));
	    highlightStory.setName(name);
	    highlightStory.setTime(LocalDateTime.now());
	    highlightStory.setStories(highlightList);
	    if(image!=null) {
	    	try {
				String imagePath = commonService.uploadImage(image, image.getName());
				highlightStory.setImage(imagePath);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    highlightService.save(highlightStory);
	  }
	
	@PostMapping("/story/save")
	public void saveStory(Integer userId, MultipartFile image, MultipartFile video) {
		Story savePost= new Story();
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
	
	@GetMapping("/story/highlight/get/{userName}")
	public List<HighlightStory> getUserHighlightStories(@PathVariable(name="userName") String userName) {
		return highlightService.getUserHighlightStories(userName);
	}
	
}