package com.cacaotalk.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng
	
	protected Integer id;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	protected LocalDateTime time;
	
	public BaseEntity() {
		this.time = LocalDateTime.now();
	}
	
	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	public Integer getId() {
		return id;
	}
	 
	public void setId(Integer id) {
		this.id = id;
	}
}
