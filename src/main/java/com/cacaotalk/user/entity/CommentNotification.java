package com.cacaotalk.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="comment_notification")
public class CommentNotification extends Notification {
	@ManyToOne
	Post post;

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	public CommentNotification() {
		this.description="has commented on your post!";
	}
	
}
