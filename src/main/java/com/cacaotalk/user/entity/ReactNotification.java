package com.cacaotalk.user.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="react_notification")
public class ReactNotification extends Notification {
	@ManyToOne
	private Post post;
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public ReactNotification() {
		this.description = "has liked your post!";
	}
}
