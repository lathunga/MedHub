package org.medhub.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.medhub.user.User;

@Entity
@Table(name="contacts")
public class Contact {
	@Id
	@Column(length = 64)
	private String id;
	@ManyToOne
	private User user;
	private String otherUserId;
	
	public Contact(String id, String userId, String otherUserId) {
		super();
		this.id = id;
		this.user = new User(userId, "","","");
		this.otherUserId = otherUserId;
	}
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getotherUserId() {
		return otherUserId;
	}
	public void setotherUserId(String otherUserId) {
		this.otherUserId = otherUserId;
	}
	
}
