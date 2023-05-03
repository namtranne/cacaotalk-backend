package com.cacaotalk.user.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="follow_notifications")
public class FollowNotification extends Notification {
	public FollowNotification() {
		this.description = "has followed you!";
	}
}
