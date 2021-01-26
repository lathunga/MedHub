package org.medhub.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll()
		.forEach(users::add);
		return users;
	}

	public void addUser(User user) {
		userRepository.save(user);
	}

	public User getUserByid(String id){
		return userRepository.findUserById(id);
	}

	public void updateUser(User user, String id) {
		userRepository.save(user);
	}

	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}
	
	public String getUserAttribute(String id, String type) {
		User user = getUserByid(id);
		
		switch (type) {
		case "id":
			return user.getid();
		case "username":
			return user.getUsername();
		case "accountType":
			return user.getAccountType();
		case "password":
			return user.getPassword();
		}
		
		return null;
		
	}

	public User getUserByName(String username) {
		return userRepository.findByUsername(username);
	}
	
	public List<String> findAllDoctorUsernames(String accountType) {
		return (List<String>) userRepository.findAllDoctorUsernames(accountType);
	}
	
	public String getId(@PathVariable String username)
	{
		return userRepository.getId(username);
	}
	
}
