package com.cacaotalk.user.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cacaotalk.user.entity.GroupChat;
import com.cacaotalk.user.entity.GroupMessage;

public interface GroupChatRepository extends PagingAndSortingRepository<GroupChat, Integer>{
	List<GroupChat> findByMembers_Id(Integer id);
}