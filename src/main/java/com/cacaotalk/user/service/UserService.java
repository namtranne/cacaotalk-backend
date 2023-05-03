package com.cacaotalk.user.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cacaotalk.user.entity.User;



@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	public List<User> listAll(){
		//return (List<User>) userRepo.findAll(); // 	
		
		return (List<User>) userRepo.findAll();
	}
	
	public User save (User user) {
		
//		boolean isUpdatingUser = (user.getId() != null);
//		
//		if (isUpdatingUser) {
//			User existingUser = userRepo.findById(user.getId()).get();
//			
//			if (user.getPassword().isEmpty()) {
//				user.setPassword(existingUser.getPassword());
//			} else {
//				encodePassword(user);
//			}
//		} else {
//			encodePassword(user);
//		}
		return userRepo.save(user);
	}
	
	
	public User get(Integer Id){
			return this.userRepo.findById(Id).get();
	}

	public void delete(Integer Id) {
		Long countById = userRepo.countById(Id);
//		if(countById == null|| countById==0) {
//			throw new UserNotFoundException("Could not find any user with ID "+ Id);
//		}
		this.userRepo.deleteById(Id);
	}
	
	public User getUserByUserName(String username) {
		return userRepo.getUserByUserName(username);
	}


//	private void encodePassword(User user) {
//		String encodePassword = passwordEncoder.encode(user.getPassword());
//		user.setPassword(encodePassword);
//	}
	
	public Set<User> getMessageList(int id) {
		Set<User> res= userRepo.getMessageList(id);
		return res;
	}
	
	public List<User> search(String keyword) {
		return userRepo.search(keyword);
	}
}
