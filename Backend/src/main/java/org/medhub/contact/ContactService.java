package org.medhub.contact;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	
	public List<Contact> getAllContacts(String userId) {
		List<Contact> contacts = new ArrayList<Contact>();
		contactRepository.findByUserId(userId).forEach(contacts::add);
		return contacts;
	}
	
	public Contact getContact(String id) {
		return contactRepository.findContactById(id);
	}
	
	public void addContact(Contact contact) {
		contactRepository.save(contact);
	}
	
	public void updateContact(Contact contact) {
		contactRepository.save(contact);
	}
	
	public void deleteContact(String id) {
		contactRepository.deleteById(id);
	}
}
