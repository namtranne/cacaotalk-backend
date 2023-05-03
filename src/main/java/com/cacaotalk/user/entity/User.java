package com.cacaotalk.user.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "users")
public class User extends BaseEntity {
	@Column(name="userName", nullable=false, length=30)
	private String userName;
	
	@Column (name="firstName", nullable= false, length=30)
	private String firstName;
	
	@Column(name="lastName", nullable=false, length=30)
	private String lastName;
	
	@Column(name="description", length=200)
	private String description;
	
	@Column(name="password", nullable=false )
	private String password;
	
	@Column(name="image") 
	private String image;
	
	 @OneToMany(mappedBy = "user")
	 private Set<Post> posts = new HashSet<>();
	
	 public User(User user) {
		 this.id=user.id;
		 this.description=user.description;
		 this.firstName=user.firstName;
		 this.lastName=user.lastName;
		 user.followers.forEach((temp)->{
			 temp.followers.clear();
			 temp.following.clear();
			 this.followers.add(temp);
		 });
		 user.following.forEach((temp)->{
			 temp.followers.clear();
			 temp.following.clear();
			 this.following.add(temp);
		 });
		 this.image=user.image;
		 this.userName=user.userName;
		 this.posts=user.posts;
	 }
	 
	 public User(String username) {
		 this.userName=username;
	 }
	 
	 public User () {
		 
	 }
	 
	 public User(String userName2, String firstName, String lastName, String image) {
		// TODO Auto-generated constructor stub
		 userName= userName2;
		 this.firstName=firstName;
		 this.lastName=lastName;
		 this.image=image;
	}
	 
	 public User(String userName2, String firstName, String lastName, String image, Integer id) {
			// TODO Auto-generated constructor stub
			 userName= userName2;
			 this.firstName=firstName;
			 this.lastName=lastName;
			 this.image=image;
			 this.id=id;
		}

	public User(String userName, String image, Integer id) {
		this.userName=userName;
		this.image=image;
		this.id=id;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	    @JoinTable(
	        name = "user_followers",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "following_user_id")
	    )
	    private Set<User> followers = new HashSet<>();

	    @ManyToMany(mappedBy = "followers", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	    private Set<User> following = new HashSet<>();
	
}
