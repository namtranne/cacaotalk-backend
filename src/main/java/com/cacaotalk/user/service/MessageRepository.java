package com.cacaotalk.user.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cacaotalk.user.entity.Message;
import com.cacaotalk.user.entity.Post;
import com.cacaotalk.user.entity.User;

public interface MessageRepository extends PagingAndSortingRepository<Message, Integer>{
	@Query("SELECT u FROM Message u WHERE (u.sender.id=:id1 and u.receiver.id=:id2) or (u.sender.id=:id2 and u.receiver.id=:id1)")
	public List<Message> getMessagesOfTwoUser(@Param("id1")Integer id1, @Param("id2")Integer id2); // ten phai giong cai param
	
	 @Modifying
	 @Transactional
	@Query("UPDATE Message u set u.isRead=true WHERE u.sender.id=:id2 and u.receiver.id=:id1")
	public void seenMessages(@Param("id1")Integer id1, @Param("id2")Integer id2); // ten phai giong cai param
}
