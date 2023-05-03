package com.cacaotalk.user.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cacaotalk.user.entity.User;



public class CacaoUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	private User user;
	
	public CacaoUserDetails (User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authories = new ArrayList<>();
			authories.add(new SimpleGrantedAuthority("User"));
		return authories;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() { //Ko lock account này
		// TODO Auto-generated method stub
		return true; //true -> account ko bị locked, false -> account bị locked
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true; //true-> ko hết hạn
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true; //kiểm tra user có enabled hay k -> ko thì ko login
	}
	
	public String getFullName() {
		return this.user.getFirstName()+" " +this.user.getLastName();
	}
	
	public String getFirstName() {
		return this.user.getFirstName();
	}
	
	public String getLastName() {
		return this.user.getLastName();
	}
	
	public void setFirstName(String firstName) {
		this.user.setFirstName(firstName);
	}
	
	public void setLastName(String lastName) {
		this.user.setLastName(lastName);
	}
	
	public boolean hasRole(String roleName) {
		return true;
	}
}