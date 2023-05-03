package com.cacaotalk.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cacaotalk.user.entity.User;
import com.cacaotalk.user.service.UserRepository;



@Service
public class CustomUserService implements UserDetailsService {
	
	@Autowired
	UserRepository userDetailsRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		

		User user=userDetailsRepository.getUserByUserName(username);
		
		if(null==user) {
			throw new UsernameNotFoundException("User Not Found with userName "+username);
		}
		return new CacaoUserDetails(user);
	}

}