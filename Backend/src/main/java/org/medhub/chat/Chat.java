package org.medhub.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.medhub.user.User;

@Entity
@Table(name = "chats")
public class Chat {

	@Id
	@Column(length = 64)
	private String id;
	@ManyToOne
	private User user;
	private String otherUserId;
	private String message;
	private String name;
	private String timestamp;
	
	public Chat(String id, String userId, String otherUserId, String message, String name, String timestamp) {
		super();
		this.id = id;
		this.user = new User(userId, "","","");
		this.otherUserId = otherUserId;
		this.message = message;
		this.name = name;
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOtherUserId() {
		return otherUserId;
	}

	public void setOtherUserId(String otherUserId) {
		this.otherUserId = otherUserId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
