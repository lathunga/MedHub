package org.medhub.user;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	// Marks this as something that needs a dependency injection.
	@Autowired
	private UserService userService;
	
	@RequestMapping("/Users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@RequestMapping("/Users/accountType/{accountType}")
	public List<String> findAllDoctorUsernames(@PathVariable String accountType) {
		return userService.findAllDoctorUsernames(accountType);
	}
	
	@RequestMapping("/Users/get/{username}")
	public String getId(@PathVariable String username)
	{
		return userService.getId(username);
	}
	
	@RequestMapping("/Users/{id}")
	public User getUser(@PathVariable String id) {
		return userService.getUserByid(id);
	}
	
	@PostMapping(value = "/Users")
	public void addUser(@RequestParam Map<String,String> body) {
		UUID userID = UUID.randomUUID();
		User user = new User(userID.toString(), body.get("username"), body.get("password"), body.get("accountType"));
		userService.addUser(user);		
	}
	
	@PutMapping(value = "/Users/{id}")
	public void updateUser(@RequestBody User User, @PathVariable String id) {
		userService.updateUser(User, id);
	}
	
	@DeleteMapping(value = "/Users/{id}")
	public void deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
	}
	
	@RequestMapping("/Users/{id}/{type}")
	public String getUserAttribute(@PathVariable String id, @PathVariable String type) {
		return userService.getUserAttribute(id, type);
	}
	
	@RequestMapping("/Users/get/{username}/{type}")
	public String getUserByName(@PathVariable String username, @PathVariable String type) {
		User user = userService.getUserByName(username);
		
		switch (type) {
		case "id":
			return user.getid();
		case "password":
			return user.getPassword();
		case "accountType":
			return user.getAccountType();
		}
		
		return username;
	}
}