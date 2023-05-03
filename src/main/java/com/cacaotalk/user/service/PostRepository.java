package com.cacaotalk.user.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.cacaotalk.user.entity.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer>{
	
}
