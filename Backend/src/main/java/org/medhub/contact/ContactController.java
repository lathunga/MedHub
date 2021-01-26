package org.medhub.contact;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.medhub.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@RequestMapping("/users/{id}/contact")
	public String getAllContacts(@PathVariable String id) {
		List<Contact> contacts = contactService.getAllContacts(id);
		String ret = "";
		
		for (int i = 0; i < contacts.size(); i++) {
			ret += contacts.get(i).getotherUserId();
			ret += (i == contacts.size() - 1)  ? "" : "@";
		}
		
		return ret;
	}
	
	@RequestMapping("/users/{userId}/contact/{id}")
	public Contact getContact(@PathVariable String id) {
		return contactService.getContact(id);
	}
	
	@PostMapping(value = "/users/{userId}/contact")
	public void addContact(@RequestParam Map<String,String> body) {
		Contact contact = new Contact(UUID.randomUUID().toString(), body.get("userId"),
				body.get("otherUserId"));
		contactService.addContact(contact);
	}
	
	@PutMapping(value = "/users/{userId}/contact/{id}")
	public void updateContact(@RequestParam Map<String,String> body, @PathVariable String id, @PathVariable String userId) {
		Contact contact = getContact(id);
		contact.setUser(new User(userId, "","",""));
		contactService.updateContact(contact);
	}
	
	@DeleteMapping(value = "/users/{userId}/contact/{id}")
	public void deleteContact(@PathVariable String id) {
		contactService.deleteContact(id);
	}
}
