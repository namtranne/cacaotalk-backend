package com.cacaotalk.user.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cacaotalk.user.entity.User;


public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	//checkDuplicateEmail
	@Query("SELECT u FROM User u WHERE u.userName = :userName")
	public User getUserByUserName(@Param("userName")String userName); // ten phai giong cai param
	
	//countBy..., findBy... => JPA
	
	public Long countById(Integer id);

	@Query("SELECT DISTINCT u " +
	           "FROM User u " +
	           "JOIN Message m ON (m.receiver = u AND m.sender.id = :givenId) OR (m.sender = u AND m.receiver.id = :givenId)")
	public Set<User> getMessageList(@Param("givenId") int id); 
	
	
	@Query("SELECT u FROM User u WHERE u.userName like %:keyword%")
	public List<User> search(@Param("keyword")String keyword); // ten phai giong cai param
	
}
