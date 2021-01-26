package org.medhub.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@Column(length = 64)
	private String id;
	private String username;
	private String password;
	private String accountType;
	
	public User() {
		this.setAccountType("doctor");
	}

	public User(String id, String username, String password, String accountType) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.accountType = accountType;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
}
