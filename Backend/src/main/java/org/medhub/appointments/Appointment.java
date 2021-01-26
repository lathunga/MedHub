package org.medhub.appointments;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.medhub.user.User;

@Entity
@Table(name="Appointments")
public class Appointment {
	@Id
	@Column(length = 64)
	private String id;
	@ManyToOne
	private User user;
	private String otherUserId;
	private String datetime;
	private String location;
	private String time;
	
	public Appointment(String id, String userId, String otherUserId, String datetime, String location, String time) {
		super();
		this.id = id;
		this.user = new User(userId, "","","");
		this.otherUserId = otherUserId;
		this.datetime = datetime;
		this.location = location;
		this.time = time;
	}
	
	public Appointment()
	{
	    
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
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String time) {
		this.time = time;
	}
	public String getTime() {
		return location;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
